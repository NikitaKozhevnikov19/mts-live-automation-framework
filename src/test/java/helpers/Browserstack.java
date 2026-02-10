package helpers;

import tests.mobile.MobileTestBase;
import static io.restassured.RestAssured.given;

public class Browserstack {
    public static String videoUrl(String sessionId) {
        String url = String.format("https://api.browserstack.com/app-automate/sessions/%s.json", sessionId);

        return given()
                .auth().basic(MobileTestBase.mobileConfig.bsUser(), MobileTestBase.mobileConfig.bsKey())
                .get(url)
                .then()
                .log().all()
                .statusCode(200)
                .extract().path("automation_session.video_url");
    }
}
