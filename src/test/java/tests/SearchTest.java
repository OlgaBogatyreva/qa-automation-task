package tests;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pages.LoginPage;
import pages.SearchPage;

import static com.codeborne.selenide.Selenide.$;

public class SearchTest extends BaseTest {

    private SearchPage loginAndGetSearchPage() {
        new LoginPage().login("test@test.test", "test");
        return new SearchPage();
    }

    // Базовый позитивный тест
    @Test
    public void shouldFindEmployeeByName() {
        SearchPage searchPage = loginAndGetSearchPage();
        searchPage.fillSearchForm("Иван", null, null, null, null, null, null);

        searchPage.checkResultCount(1);
        searchPage.checkTableContainsName("Иван");
    }

    // Негативный тест (пустой результат)
    @Test
    public void shouldShowEmptyStateWhenNothingFound() {
        SearchPage searchPage = loginAndGetSearchPage();
        searchPage.fillSearchForm("Меня нет", null, null, null, null, null, null);

        searchPage.checkEmptyState();
    }

    // Проверка сброса
    @Test
    public void shouldResetFilters() {
        SearchPage searchPage = loginAndGetSearchPage();
        searchPage.fillSearchForm("Иван", null, "Мужской", null, null, null, "Selenium");
        searchPage.checkResultCount(1);

        searchPage.clickReset();

        $("#searchName").shouldBe(Condition.empty);
        searchPage.checkResultCount(8); // В базе 8 человек
    }

    @Test
    public void shouldLogoutSuccessfully() {
        SearchPage searchPage = loginAndGetSearchPage();
        LoginPage loginPage = searchPage.logout();

        com.codeborne.selenide.Selenide.$("h1").shouldHave(com.codeborne.selenide.Condition.text("Вход в систему"));
        com.codeborne.selenide.Selenide.$("#loginEmail").shouldBe(com.codeborne.selenide.Condition.empty);
        com.codeborne.selenide.Selenide.$("#loginPassword").shouldBe(com.codeborne.selenide.Condition.empty);
    }

    // Параметризованный (Попарные комбинации)
    @ParameterizedTest(name = "Фильтры: имя={0}, email={1}, пол={2}, возраст={3}, даты={4}..{5}, навык={6} -> ожидаем {7} чел.")
    @CsvSource({
            // Имя + Пол
            "Иван, , Мужской, , , , , 1",

            // Email + Навык
            ", ivanov@company.ru, , , , , 'Selenium', 1",

            // Пол + Возраст
            ", , Женский, 31, , , , 1",

            // Возраст + Дата
            ", , , 28, 2020-01-01, , , 1",

            // Навык + Пол
            ", , Мужской, , , , 'API', 2",

            // Комбинация, которая ничего не даст
            " , , Женский, '20', , , 'Selenium', 0"
    })
    public void shouldFilterByPairwiseCombinations(String name, String email, String gender, String age, String dateFrom, String dateTo, String skill, int expectedCount) {
        SearchPage searchPage = loginAndGetSearchPage();

        searchPage.fillSearchForm(name, email, gender, age, dateFrom, dateTo, skill);
        searchPage.checkResultCount(expectedCount);
    }

    @Test
    public void shouldShowErrorWhenDateFromIsGreaterThanDateTo() {
        SearchPage searchPage = loginAndGetSearchPage();

        // Вводим некорректный период (с 2025 по 2020)
        searchPage.fillSearchForm(null, null, null, null, "2025-01-01", "2020-01-01", null);

        // Проверяем, что появилась плашка с ошибкой
        $("#searchAlertsHolder").shouldBe(Condition.visible)
                .shouldHave(Condition.text("Дата «с» не может быть позже даты «по»"));
    }

}