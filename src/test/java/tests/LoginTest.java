package tests;

import org.junit.jupiter.api.Test;
import pages.LoginPage;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class LoginTest extends BaseTest {

    @Test
    public void shouldLoginSuccessfully() {
        LoginPage loginPage = new LoginPage();
        loginPage.login("test@test.test", "test");
        $(".app-header h1").shouldHave(text("Поиск сотрудников"));
    }

    @Test
    public void shouldShowErrorForInvalidEmail() {
        LoginPage loginPage = new LoginPage();
        loginPage.login("invalid-email", "test");

        loginPage.checkError("Неверный формат Email");
    }

    @Test
    public void shouldShowErrorForInvalidPwd() {
        LoginPage loginPage = new LoginPage();
        loginPage.login("test@test.test", "123");

        loginPage.checkError("Неверный Email или пароль");
    }
}