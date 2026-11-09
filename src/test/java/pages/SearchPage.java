package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class SearchPage {
    // Основные поля
    private final SelenideElement nameInput = $("#searchName");
    private final SelenideElement emailInput = $("#searchEmail");
    private final SelenideElement genderSelect = $("#searchGender");
    private final SelenideElement ageInput = $("#searchAge");
    private final SelenideElement dateFromInput = $("#searchDateFrom");
    private final SelenideElement dateToInput = $("#searchDateTo");

    // Кнопки и результаты
    private final SelenideElement searchButton = $("#searchButton");
    private final SelenideElement resetButton = $("#resetSearchButton");
    private final SelenideElement resultsCount = $("#resultsCount");
    private final ElementsCollection tableRows = $$("#resultsTable tbody tr");


    public void checkResultCount(int count) {
        resultsCount.shouldHave(Condition.text("Найдено: " + count));
    }

    public void checkTableContainsName(String name) {
        tableRows.findBy(Condition.text(name)).shouldBe(Condition.visible);
    }

    public void checkEmptyState() {
        resultsCount.shouldHave(Condition.text("Найдено: 0"));
        $("#resultsEmpty").shouldBe(Condition.visible);
    }

    // Действия
    public void clickSearch() {
        searchButton.click();
    }

    public void clickReset() {
        resetButton.click();
    }

    public LoginPage logout() {
        $("#logoutButton").click();
        return new LoginPage();
    }

    // Универсальный метод для параметризованных тестов
    public void fillSearchForm(String name, String email, String gender, String age, String dateFrom, String dateTo, String skill) {
        if (name != null && !name.isEmpty()) nameInput.setValue(name);
        if (email != null && !email.isEmpty()) emailInput.setValue(email);
        if (gender != null && !gender.isEmpty()) genderSelect.selectOption(gender);
        if (age != null && !age.isEmpty()) ageInput.setValue(age);
        if (dateFrom != null && !dateFrom.isEmpty()) dateFromInput.setValue(dateFrom);
        if (dateTo != null && !dateTo.isEmpty()) dateToInput.setValue(dateTo);
        if (skill != null && !skill.isEmpty()) $("input[type='checkbox'][value='" + skill + "']").click();

        clickSearch();
    }
}