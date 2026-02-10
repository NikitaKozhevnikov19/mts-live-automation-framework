package drivers;

import com.codeborne.selenide.WebDriverProvider;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import tests.mobile.MobileTestBase;

import javax.annotation.Nonnull;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Map;

public class AndroidDriverProvider implements WebDriverProvider {

    @Nonnull
    @Override
    public WebDriver createDriver(@Nonnull Capabilities capabilities) {
        UiAutomator2Options options = new UiAutomator2Options();

        String appPackage = "ru.mts.live.app";
        String appActivity = "ru.stream.mtsliveapp.ui.MtsLiveActivity";
        String appPath = MobileTestBase.mobileConfig.appUrl().trim();

        if (MobileTestBase.deviceHost.contains("bs") || MobileTestBase.deviceHost.equals("browserstack")) {
            options.setDeviceName(MobileTestBase.mobileConfig.deviceName())
                    .setPlatformVersion(MobileTestBase.mobileConfig.platformVersion())
                    .setApp(appPath)
                    .setCapability("bstack:options", Map.of(
                            "userName", MobileTestBase.mobileConfig.bsUser(),
                            "accessKey", MobileTestBase.mobileConfig.bsKey(),
                            "projectName", "MTS Live Project",
                            "buildName", "android-build-mts"
                    ));
        } else {
            if (appPath.startsWith("src/test/resources")) {
                File app = new File(appPath);
                if (!app.exists()) {
                    throw new RuntimeException("APK файл не найден по пути: " + app.getAbsolutePath());
                }
                appPath = app.getAbsolutePath();
            }

            options.setDeviceName(MobileTestBase.mobileConfig.deviceName())
                    .setPlatformVersion(MobileTestBase.mobileConfig.platformVersion())
                    .setApp(appPath)
                    .setAppPackage(appPackage)
                    .setAppActivity(appActivity)
                    .setNewCommandTimeout(Duration.ofSeconds(60));
        }

        options.setAutomationName(MobileTestBase.mobileConfig.automationName());

        try {
            return new AndroidDriver(new URL(MobileTestBase.mobileConfig.remoteUrl()), options);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Ошибка URL драйвера: " + MobileTestBase.mobileConfig.remoteUrl(), e);
        }
    }
}
