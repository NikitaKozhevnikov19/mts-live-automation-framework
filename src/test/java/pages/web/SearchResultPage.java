package pages.web;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class SearchResultPage {

    private final SelenideElement notFoundTitle = $("[class*='NotFound_title']");

    private final ElementsCollection allTitlesAndCards = $$("h1, h2, a[data-type='nazvanie_meropriyatiya']");

    @Step("Проверить заголовок или наличие карточки с текстом: {expectedText}")
    public void verifySearchTitle(String expectedText) {
        allTitlesAndCards.findBy(text(expectedText))
                .shouldBe(exist, Duration.ofSeconds(15));
    }

    @Step("Проверить текст отсутствия результатов: {expectedText}")
    public void verifyEmptySearchResult(String expectedText) {
        notFoundTitle.shouldBe(visible, Duration.ofSeconds(10))
                .shouldHave(text(expectedText));
    }
}
