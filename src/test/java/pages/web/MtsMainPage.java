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
            footer = $("footer"),
            nextSlideBtn = $("button[aria-label='Следующий слайд']");

    private final ElementsCollection
            headerLinks = $$("a[class*='NavigationMenuItem_link']");

    @Step("Открыть главную страницу МТС Live")
    public void openPage() {
        open("/");
    }

    @Step("Выполнить поиск по запросу: {query}")
    public void searchEntity(String query) {
        searchInput.shouldBe(visible, Duration.ofSeconds(10))
                .click(ClickOptions.usingJavaScript());
        searchInput.setValue(query).pressEnter();
    }

    @Step("Нажать на категорию в меню: {categoryName}")
    public void clickHeaderCategory(String categoryName) {
        headerLinks.findBy(text(categoryName))
                .shouldBe(visible, Duration.ofSeconds(10))
                .click(ClickOptions.usingJavaScript());
    }

    @Step("Сменить город на: {cityName}")
    public void changeCity(String cityName) {
        cityButton.shouldBe(visible).click(ClickOptions.usingJavaScript());
        $(byTagAndText("a", cityName))
                .shouldBe(exist, Duration.ofSeconds(5))
                .click(ClickOptions.usingJavaScript());
    }

    @Step("Проверить текущий выбранный город: {cityName}")
    public void verifyCurrentCity(String cityName) {
        cityButton.shouldHave(text(cityName), Duration.ofSeconds(10));
    }

    @Step("Проскроллить страницу до футера и проверить его видимость")
    public void verifyFooterVisible() {
        footer.scrollTo().shouldBe(visible);
    }

    @Step("Нажать на стрелку 'Следующий слайд'")
    public void clickNextSlide() {
        nextSlideBtn.shouldBe(visible, Duration.ofSeconds(10))
                .click(ClickOptions.usingJavaScript());
    }

    @Step("Проверить, что слайдер отображается")
    public void verifySliderWorks() {
        $("button[aria-label='Следующий слайд']").shouldBe(visible);
    }
}
