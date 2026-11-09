package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private final SelenideElement emailInput = $("#loginEmail");
    private final SelenideElement passwordInput = $("#loginPassword");
    private final SelenideElement loginButton = $("#authButton");
    private final SelenideElement alertBox = $("#authAlertsHolder");

    public void login(String email, String password) {
        emailInput.setValue(email);
        passwordInput.setValue(password);
        loginButton.click();
    }

    public void checkError(String expectedText) {
        alertBox.shouldBe(Condition.visible).shouldHave(Condition.text(expectedText));
    }
}