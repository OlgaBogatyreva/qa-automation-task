package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.net.URL;

public class BaseTest {

    @BeforeAll
    public static void setUp() {
        Configuration.browser = "chrome";
        Configuration.timeout = 5000;
    }

    @BeforeEach
    public void openPage() {
        URL url = BaseTest.class.getClassLoader().getResource("pages/index.html");
        if (url == null) {
            throw new RuntimeException("Файл index.html не найден в src/test/resources/pages/");
        }
        Selenide.open(url.toString());
    }

    @AfterEach
    public void tearDown() {
        Selenide.closeWebDriver();
    }
}