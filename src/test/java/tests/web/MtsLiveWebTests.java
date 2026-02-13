package tests.web;

import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.web.MtsMainPage;
import pages.web.SearchResultPage;
import pages.web.components.CalendarComponent;

import java.time.LocalDate;

@Epic("Web MTS Live")
@Feature("Основные сценарии сайта")
@Owner("Nikita Kozhevnikov")
@Tag("web")
public class MtsLiveWebTests extends WebTestBase {

    private final MtsMainPage mainPage = new MtsMainPage();
    private final SearchResultPage resultsPage = new SearchResultPage();
    private final CalendarComponent calendar = new CalendarComponent();

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("1. WEB: Поиск мероприятия по названию")
    void successfulSearchTest() {
        mainPage.openPage();
        mainPage.searchEntity("Концерт");

        resultsPage.verifySearchTitle("Найдено по запросу");
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("2. WEB: Смена города на Санкт-Петербург")
    void changeCityTest() {
        mainPage.openPage();
        mainPage.changeCity("Санкт-Петербург");

        mainPage.verifyCurrentCity("Санкт-Петербург");
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("3. WEB: Поиск несуществующего события")
    void negativeSearchTest() {
        mainPage.openPage();
        mainPage.searchEntity("zxcvbnm123456");

        resultsPage.verifyEmptySearchResult("Ничего не найдено");
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("4. WEB: Переход в раздел Шоу через хедер")
    void navigationToShowsTest() {
        mainPage.openPage();
        mainPage.clickHeaderCategory("Шоу");

        resultsPage.verifySearchTitle("Шоу");
    }

    @Test
    @DisplayName("5. WEB: Фильтрация мероприятий по датам")
    void filterByDateRangeTest() {
        String startDay = String.valueOf(LocalDate.now().plusDays(1).getDayOfMonth());
        String endDay = String.valueOf(LocalDate.now().plusDays(2).getDayOfMonth());

        mainPage.openPage();
        calendar.openCalendar();
        calendar.selectDateRange(startDay, endDay);

        calendar.verifyDateFilterApplied(startDay, endDay);
    }

    @Test
    @Severity(SeverityLevel.MINOR)
    @DisplayName("6. WEB: Проверка видимости футера")
    void footerVisibilityTest() {
        mainPage.openPage();
        mainPage.verifyFooterVisible();
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("7. WEB: Проверка переключения баннеров в слайдере")
    void sliderComponentTest() {
        mainPage.openPage();
        mainPage.clickNextSlide();
        mainPage.verifySliderWorks();
    }
}
