package tests.web;

import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.web.MtsMainPage;

import java.time.LocalDate;

@Epic("Web MTS Live")
@Feature("Основные сценарии сайта")
@Owner("Nikita Kozhevnikov")
@Tag("web")
public class MtsLiveWebTests extends WebTestBase {

    private final MtsMainPage mainPage = new MtsMainPage();

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("1. WEB: Поиск мероприятия по названию")
    void successfulSearchTest() {
        mainPage.openPage().searchEntity("Концерт").verifySearchTitle("Найдено по запросу");
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("2. WEB: Смена города на Санкт-Петербург")
    void changeCityTest() {
        mainPage.openPage().changeCity("Санкт-Петербург").verifyCurrentCity("Санкт-Петербург");
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("3. WEB: Поиск несуществующего события")
    void negativeSearchTest() {
        mainPage.openPage().searchEntity("zxcvbnm123456").verifyEmptySearchResult("Ничего не найдено");
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("4. WEB: Переход в раздел Шоу через хедер")
    void navigationToShowsTest() {
        mainPage.openPage().clickHeaderCategory("Шоу").verifySearchTitle("Шоу");
    }

    @Test
    @DisplayName("5. WEB: Фильтрация мероприятий по датам")
    void filterByDateRangeTest() {
        String startDay = String.valueOf(LocalDate.now().plusDays(1).getDayOfMonth());
        String endDay = String.valueOf(LocalDate.now().plusDays(2).getDayOfMonth());

        mainPage.openPage()
                .selectDateRange(startDay, endDay)
                .verifyDateFilterApplied(startDay, endDay);
    }

    @Test
    @Severity(SeverityLevel.MINOR)
    @DisplayName("6. WEB: Проверка видимости футера")
    void footerVisibilityTest() {
        mainPage.openPage().verifyFooterVisible();
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("7. WEB: Проверка переключения баннеров в слайдере")
    void sliderComponentTest() {
        mainPage.openPage()
                .clickNextSlide()
                .verifySliderWorks();
    }
}
