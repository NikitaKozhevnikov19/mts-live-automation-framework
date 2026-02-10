package pages.android;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.appium.java_client.AppiumBy;
import io.qameta.allure.Step;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

import java.time.Duration;
import java.util.List;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class SearchPage {

    private final SelenideElement
            whatElseBtn = $(AppiumBy.accessibilityId("А что ещё?. Кнопка")),
            laterBtn = $(AppiumBy.accessibilityId("Позже. Кнопка")),
            notNowBtn = $(AppiumBy.xpath("//android.view.View[@content-desc='Спасибо, не надо. Кнопка']")),
            skipLoginBtn = $(AppiumBy.id("ru.mts.live.app:id/rightLabel")),
            closeCityBtn = $(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.Button\").instance(1)")),
            cityHeader = $(AppiumBy.androidUIAutomator("new UiSelector().text(\"Выбор города\")")),
            alertCancelBtn = $(AppiumBy.accessibilityId("Отмена.Кнопка")),
            fakeSearchField = $(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.view.View\").instance(3)")),
            concertsCategoryBtn = $(AppiumBy.androidUIAutomator("new UiSelector().textContains(\"Концерты\")")),
            concertsDescBtn = $(AppiumBy.androidUIAutomator("new UiSelector().descriptionContains(\"Концерты\")")),
            firstEventCard = $(AppiumBy.xpath("//androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View[@index='4']/android.view.View[@index='0']")),
            priceTicketsBtn = $(AppiumBy.androidUIAutomator("new UiSelector().textContains(\"От \")")),
            purchaseContainer = $(AppiumBy.id("ru.mts.live.app:id/constraintlayout_ticketpurchase")),
            scrollableElement = $(AppiumBy.androidUIAutomator(
                    "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(" +
                            "new UiSelector().textContains(\"От \"))"));

    @Step("Пропустить онбординг")
    public void skipAllOnboarding() {
        whatElseBtn.shouldBe(visible, Duration.ofSeconds(40)).click();
        laterBtn.shouldBe(visible, Duration.ofSeconds(15)).click();
        notNowBtn.shouldBe(visible, Duration.ofSeconds(15)).click();

        if (skipLoginBtn.is(visible)) {
            skipLoginBtn.click();
        }
        handleCityAndAlerts();
    }

    @Step("Обработка окон города")
    public void handleCityAndAlerts() {
        sleep(3000);
        if (closeCityBtn.exists()) {
            closeCityBtn.click();
        } else if (cityHeader.is(visible)) {
            tapByCoordinates(0.92, 0.09);
        }

        if (alertCancelBtn.is(visible)) {
            alertCancelBtn.click();
        }
    }

    @Step("Выбрать категорию 'Концерты'")
    public void selectCategoryAndFutureDate() {
        fakeSearchField.shouldBe(visible, Duration.ofSeconds(20)).click();
        sleep(3000);

        if (cityHeader.is(visible)) {
            closeCityBtn.click();
        }

        if (concertsCategoryBtn.is(visible)) {
            concertsCategoryBtn.click();
        } else {
            concertsDescBtn.click();
        }
    }

    @Step("Проверить результаты")
    public void checkResultsVisible() {
        try {
            if (((io.appium.java_client.android.AndroidDriver) getWebDriver()).isKeyboardShown()) {
                back();
            }
        } catch (Exception ignored) {
        }

        sleep(5000);

        if (!firstEventCard.should(Condition.exist, Duration.ofSeconds(30)).exists()) {
            tapByCoordinates(0.5, 0.5);
            firstEventCard.should(Condition.exist, Duration.ofSeconds(15));
        }
    }

    @Step("Открыть событие и найти кнопку покупки")
    public void openFirstEvent() {
        firstEventCard.should(Condition.exist, Duration.ofSeconds(20));
        sleep(2000);
        tapElementCenter(firstEventCard);
        sleep(5000);
        scrollableElement.shouldBe(visible, Duration.ofSeconds(30));
    }

    @Step("Нажать на кнопку с ценой")
    public void clickBuyButton() {
        priceTicketsBtn.shouldBe(visible, Duration.ofSeconds(20));
        tapElementCenter(priceTicketsBtn);
    }

    @Step("Проверить загрузку виджета покупки")
    public void checkWidgetLoading() {
        priceTicketsBtn.should(Condition.disappear, Duration.ofSeconds(15));
        purchaseContainer.should(Condition.exist, Duration.ofSeconds(30));
    }

    private void tapElementCenter(SelenideElement element) {
        Rectangle rect = element.getRect();
        tapByAbsoluteCoordinates(rect.getX() + rect.getWidth() / 2, rect.getY() + rect.getHeight() / 2);
    }

    private void tapByCoordinates(double xPct, double yPct) {
        Dimension size = getWebDriver().manage().window().getSize();
        tapByAbsoluteCoordinates((int) (size.width * xPct), (int) (size.height * yPct));
    }

    private void tapByAbsoluteCoordinates(int x, int y) {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence tap = new Sequence(finger, 1);
        tap.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x, y));
        tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        ((io.appium.java_client.AppiumDriver) getWebDriver()).perform(List.of(tap));
    }
}
