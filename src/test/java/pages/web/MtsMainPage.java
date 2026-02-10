package pages.web;

import com.codeborne.selenide.ClickOptions;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byTagAndText;
import static com.codeborne.selenide.Selenide.*;

public class MtsMainPage {

    private final SelenideElement
            cityButton = $("button[title='Выбор региона']"),
            searchInput = $("input[type='search']"),
            notFoundTitle = $("[class*='NotFound_title']"),
            footer = $("footer"),
            dateFilterBtn = $("button[aria-label='Календарь']"),
            applyDatesBtn = $(byTagAndText("button", "Применить")),
            nextSlideBtn = $("button[aria-label='Следующий слайд']"),
            calendarPopup = $(".DateRangePicker_calendar__i19Dc");

    private final ElementsCollection
            headerLinks = $$("a[class*='NavigationMenuItem_link']"),
            allTitlesAndCards = $$("h1, h2, a[data-type='nazvanie_meropriyatiya']"),
            calendarDays = $$("button[class*='CalendarDates_date'], [class*='CalendarDay']"),
            selectedDays = $$("button[class*='Button_alternativeTheme']"),
            banners = $$("[class*='Banner_wrapper']");

    @Step("Открыть главную страницу МТС Live")
    public MtsMainPage openPage() {
        open("/");
        return this;
    }

    @Step("Выполнить поиск по запросу: {query}")
    public MtsMainPage searchEntity(String query) {
        searchInput.shouldBe(visible, Duration.ofSeconds(10)).click();
        searchInput.setValue(query).pressEnter();
        return this;
    }

    @Step("Проверить заголовок или наличие карточки с текстом: {expectedText}")
    public MtsMainPage verifySearchTitle(String expectedText) {
        allTitlesAndCards.findBy(text(expectedText))
                .shouldBe(exist, Duration.ofSeconds(15));
        return this;
    }

    @Step("Нажать на категорию в меню: {categoryName}")
    public MtsMainPage clickHeaderCategory(String categoryName) {
        headerLinks.findBy(text(categoryName))
                .shouldBe(visible, Duration.ofSeconds(10))
                .click(ClickOptions.usingJavaScript());
        return this;
    }

    @Step("Выбрать диапазон дат в календаре: с {startDay} по {endDay}")
    public MtsMainPage selectDateRange(String startDay, String endDay) {
        dateFilterBtn.shouldBe(visible, Duration.ofSeconds(10)).click();

        calendarDays.filter(text(startDay)).first()
                .shouldBe(visible, Duration.ofSeconds(5))
                .click(ClickOptions.usingJavaScript());

        calendarDays.filter(text(endDay)).first()
                .shouldBe(visible, Duration.ofSeconds(5))
                .click(ClickOptions.usingJavaScript());

        if (applyDatesBtn.is(visible)) {
            applyDatesBtn.click(ClickOptions.usingJavaScript());
        }
        return this;
    }

    @Step("Проверить, что в календаре выделены выбранные даты: {startDay} и {endDay}")
    public MtsMainPage verifyDateFilterApplied(String startDay, String endDay) {
        selectedDays.filter(visible).findBy(text(startDay)).should(exist, Duration.ofSeconds(10));
        selectedDays.filter(visible).findBy(text(endDay)).should(exist, Duration.ofSeconds(10));
        return this;
    }

    @Step("Сменить город на: {cityName}")
    public MtsMainPage changeCity(String cityName) {
        cityButton.shouldBe(visible).click();
        $(byTagAndText("a", cityName))
                .shouldBe(exist, Duration.ofSeconds(5))
                .click(ClickOptions.usingJavaScript());
        return this;
    }

    @Step("Проверить текущий выбранный город: {cityName}")
    public MtsMainPage verifyCurrentCity(String cityName) {
        cityButton.shouldHave(text(cityName), Duration.ofSeconds(10));
        return this;
    }

    @Step("Проверить текст отсутствия результатов: {expectedText}")
    public MtsMainPage verifyEmptySearchResult(String expectedText) {
        notFoundTitle.shouldBe(visible, Duration.ofSeconds(10)).shouldHave(text(expectedText));
        return this;
    }

    @Step("Проскроллить страницу до футера и проверить его видимость")
    public MtsMainPage verifyFooterVisible() {
        footer.scrollTo().shouldBe(visible);
        return this;
    }

    @Step("Нажать на стрелку 'Следующий слайд'")
    public MtsMainPage clickNextSlide() {
        nextSlideBtn.shouldBe(visible, Duration.ofSeconds(10)).click();
        return this;
    }

    @Step("Проверить, что слайды переключаются")
    public MtsMainPage verifySliderWorks() {
        nextSlideBtn.shouldBe(visible);
        return this;
    }
}
