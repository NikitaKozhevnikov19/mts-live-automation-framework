package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomApiListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.ALL;
import static io.restassured.http.ContentType.JSON;

public class ReqresSpecs {
    public static final RequestSpecification reqresRequestSpec = with()
            .filter(withCustomTemplates())
            .header("x-api-key", "reqres_e55e36d5f2f54397a8690b2a6aa325da")
            .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
            .log().all()
            .baseUri("https://reqres.in")
            .basePath("/api")
            .contentType(JSON);

    public static final ResponseSpecification resOk200 = new ResponseSpecBuilder()
            .log(ALL)
            .expectStatusCode(200)
            .build();

    public static final ResponseSpecification resCreated201 = new ResponseSpecBuilder()
            .log(ALL)
            .expectStatusCode(201)
            .build();
}
