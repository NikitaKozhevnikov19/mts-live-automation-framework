package helpers;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.nio.charset.StandardCharsets;

import static com.codeborne.selenide.Selenide.sessionId;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static org.openqa.selenium.logging.LogType.BROWSER;

public class Attachments {

    @Attachment(value = "{screenshotName}", type = "image/png")
    public static byte[] screenshotAs(String screenshotName) {
        return ((TakesScreenshot) getWebDriver()).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "Page source", type = "text/plain")
    public static byte[] pageSource() {
        return getWebDriver().getPageSource().getBytes(StandardCharsets.UTF_8);
    }

    // Для веба (Selenoid)
    @Attachment(value = "Video", type = "text/html", fileExtension = ".html")
    public static String addVideo() {
        if (Configuration.remote == null) {
            return "Видео доступно только при удалённом запуске";
        }

        String remoteUrl = Configuration.remote; // например: https://user:pass@selenoid.autotests.cloud/wd/hub
        String baseUrl = remoteUrl.replace("/wd/hub", "");
        String videoUrl = baseUrl + "/video/" + sessionId() + ".mp4";

        return "<html><body>" +
                "<video width='100%' height='100%' controls autoplay>" +
                "<source src='" + videoUrl + "' type='video/mp4'>" +
                "</video></body></html>";
    }


    // Для мобилок (Browserstack)
    @Attachment(value = "Video", type = "text/html", fileExtension = ".html")
    public static String addVideo(String sessionId) {
        return "<html><body><video width='100%' height='100%' controls autoplay><source src='"
                + helpers.Browserstack.videoUrl(sessionId)
                + "' type='video/mp4'></video></body></html>";
    }

    @Attachment(value = "Browser console logs", type = "text/plain")
    public static String browserConsoleLogs() {
        return String.join("\n", Selenide.getWebDriverLogs(BROWSER));
    }
}
