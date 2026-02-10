package tests.mobile;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import config.MobileConfig;
import drivers.AndroidDriverProvider;
import helpers.Attachments;
import io.qameta.allure.selenide.AllureSelenide;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MobileTestBase {

    public static final String deviceHost = System.getProperty("deviceHost", "emulation");
    public static final MobileConfig mobileConfig = ConfigFactory.create(MobileConfig.class, System.getProperties());

    @BeforeAll
    static void setup() {
        Configuration.browserSize = null;
        Configuration.reportsFolder = "build/allure-results";
        Configuration.timeout = 30000;
    }

    @BeforeEach
    void startDriver() {
        Configuration.browser = AndroidDriverProvider.class.getName();
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                .screenshots(true)
                .savePageSource(true));
        Selenide.open();
    }

    @AfterEach
    void tearDown() {
        if (WebDriverRunner.hasWebDriverStarted()) {
            String sessionId = ((RemoteWebDriver) WebDriverRunner.getWebDriver()).getSessionId().toString();

            Attachments.screenshotAs("Last screenshot");
            Selenide.closeWebDriver();

            if (deviceHost.contains("bs") || deviceHost.equals("browserstack")) {
                try {
                    Thread.sleep(5000);
                    Attachments.addVideo(sessionId);
                } catch (Exception e) {
                    System.err.println("Browserstack Video error: " + e.getMessage());
                }
            }
        }
    }
}
