package tests.mobile;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.android.SearchPage;

@Epic("Mobile Automation")
@Feature("MTS Live App")
@Tag("android")
@Owner("Kozherka")
public class MtsLiveMobileTests extends MobileTestBase {

    SearchPage searchScreen = new SearchPage();

    @Test
    @DisplayName("Android: Поиск мероприятий (Концерты на будущую дату)")
    void searchFutureEventsTest() {
        searchScreen.skipAllOnboarding();
        searchScreen.selectCategoryAndFutureDate();
        searchScreen.checkResultsVisible();
    }

    @Test
    @DisplayName("Android: Переход в карточку первого события в списке")
    void openEventCardTest() {
        searchScreen.skipAllOnboarding();
        searchScreen.selectCategoryAndFutureDate();
        searchScreen.openFirstEvent();
    }

    @Test
    @DisplayName("Android: Проверка запуска покупки билетов (виджет)")
    void startPurchaseFlowTest() {
        searchScreen.skipAllOnboarding();
        searchScreen.selectCategoryAndFutureDate();
        searchScreen.openFirstEvent();
        searchScreen.clickBuyButton();
        searchScreen.checkWidgetLoading();
    }
}
