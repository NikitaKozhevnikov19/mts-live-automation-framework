package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomApiListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.ALL;
import static io.restassured.http.ContentType.JSON;

public class MtsSpecs {
    public static final RequestSpecification searchRequestSpec = with()
            .filter(withCustomTemplates())
            .header("User-Agent", "Mozilla/5.0")//
            .log().all()
            .baseUri("https://live.mts.ru")
            .basePath("/fapi")
            .contentType(JSON);

    public static final ResponseSpecification searchResponseSpec = new ResponseSpecBuilder()
            .log(ALL)
            .expectStatusCode(200)
            .build();
}
