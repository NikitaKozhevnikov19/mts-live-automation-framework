package tests.web;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import helpers.Attachments;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Map;

import static com.codeborne.selenide.WebDriverRunner.closeWebDriver;

public class WebTestBase {

    @BeforeAll
    static void setup() {
        Configuration.baseUrl = "https://live.mts.ru";
        Configuration.browser = System.getProperty("browser", "chrome");
        Configuration.browserVersion = System.getProperty("browserVersion", "121.0");
        Configuration.browserSize = System.getProperty("browserSize", "1920x1080");
        Configuration.timeout = 10000;
        Configuration.pageLoadStrategy = "eager";

        String remoteUrl = System.getProperty("remoteUrl");
        if (remoteUrl != null) {
            Configuration.remote = remoteUrl;
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("selenoid:options", Map.of(
                    "enableVNC", true,
                    "enableVideo", true
            ));
            Configuration.browserCapabilities = capabilities;
        }

        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
    }

    @AfterEach
    void tearDown() {
        Attachments.screenshotAs("Last screenshot");
        Attachments.pageSource();

        if (!Configuration.browser.equalsIgnoreCase("firefox")) {
            Attachments.browserConsoleLogs();
        }

        if (Configuration.remote != null) {
            Attachments.addVideo();
        }

        closeWebDriver();
    }
}
