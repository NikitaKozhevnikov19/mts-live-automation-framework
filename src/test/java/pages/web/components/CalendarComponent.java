package pages.web.components;

import com.codeborne.selenide.ClickOptions;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byTagAndText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CalendarComponent {

    private final SelenideElement
            dateFilterBtn = $("button[aria-label='Календарь']"),
            applyDatesBtn = $(byTagAndText("button", "Применить"));

    private final ElementsCollection
            calendarDays = $$("button[class*='CalendarDates_date'], [class*='CalendarDay']"),
            selectedDays = $$("button[class*='Button_alternativeTheme']");

    @Step("Открыть календарь")
    public void openCalendar() {
        dateFilterBtn.shouldBe(visible, Duration.ofSeconds(10))
                .click(ClickOptions.usingJavaScript());
    }

    @Step("Выбрать диапазон дат: с {startDay} по {endDay}")
    public void selectDateRange(String startDay, String endDay) {
        calendarDays.filter(text(startDay)).first()
                .shouldBe(visible, Duration.ofSeconds(5))
                .click(ClickOptions.usingJavaScript());

        calendarDays.filter(text(endDay)).first()
                .shouldBe(visible, Duration.ofSeconds(5))
                .click(ClickOptions.usingJavaScript());

        if (applyDatesBtn.is(visible)) {
            applyDatesBtn.click(ClickOptions.usingJavaScript());
        }
    }

    @Step("Проверить выделение дат в календаре: {startDay} и {endDay}")
    public void verifyDateFilterApplied(String startDay, String endDay) {
        selectedDays.filter(visible).findBy(text(startDay)).should(exist, Duration.ofSeconds(10));
        selectedDays.filter(visible).findBy(text(endDay)).should(exist, Duration.ofSeconds(10));
    }
}
