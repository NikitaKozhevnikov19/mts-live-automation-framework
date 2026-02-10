package tests.api;

import io.qameta.allure.*;
import models.api.*;
import org.junit.jupiter.api.*;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.ReqresSpecs.*;

@Epic("API Reqres.in")
@Feature("CRUD операции пользователя")
@Tag("api")
@Owner("Nikita Kozhevnikov") // Добавь свое имя по требованию ТЗ
public class ReqresApiTests extends ApiTestBase {

    @Test
    @DisplayName("1. GET: Список пользователей (Read)")
    @Severity(SeverityLevel.BLOCKER)
    void getListUsersTest() {
        UserListResponse response = step("Запросить список пользователей", () ->
                given(reqresRequestSpec)
                        .when()
                        .get("/users?page=2")
                        .then()
                        .spec(resOk200)
                        .extract().as(UserListResponse.class));

        step("Проверить, что email второго пользователя корректен", () ->
                assertThat(response.getData().get(1).getEmail()).isEqualTo("lindsay.ferguson@reqres.in"));
    }

    @Test
    @DisplayName("2. POST: Создание пользователя (Create)")
    @Severity(SeverityLevel.CRITICAL)
    void createUserTest() {
        UserJson user = new UserJson("morpheus", "leader");

        UserJsonPostResponse response = step("Создать нового пользователя", () ->
                given(reqresRequestSpec)
                        .body(user)
                        .when()
                        .post("/users")
                        .then()
                        .spec(resCreated201)
                        .extract().as(UserJsonPostResponse.class));

        step("Проверить имя созданного пользователя", () ->
                assertThat(response.getName()).isEqualTo("morpheus"));
    }

    @Test
    @DisplayName("3. PUT: Полное обновление данных (Update)")
    void updateUserPutTest() {
        UserJson user = new UserJson("morpheus", "zion resident");

        UserJsonPutResponse response = step("Обновить пользователя через PUT", () ->
                given(reqresRequestSpec)
                        .body(user)
                        .when()
                        .put("/users/2")
                        .then()
                        .spec(resOk200)
                        .extract().as(UserJsonPutResponse.class));

        step("Проверить новую работу пользователя", () ->
                assertThat(response.getJob()).isEqualTo("zion resident"));
    }

    @Test
    @DisplayName("4. PATCH: Частичное обновление (Update)")
    void updateUserPatchTest() {
        UserJson user = new UserJson();
        user.setJob("zion resident");

        step("Обновить поле 'job' через PATCH", () ->
                given(reqresRequestSpec)
                        .body(user)
                        .when()
                        .patch("/users/2")
                        .then()
                        .spec(resOk200)
                        .body("job", org.hamcrest.Matchers.is("zion resident")));
    }

    @Test
    @DisplayName("5. DELETE: Удаление пользователя (Delete)")
    void deleteUserTest() {
        step("Удалить пользователя с ID 2", () ->
                given(reqresRequestSpec)
                        .when()
                        .delete("/users/2")
                        .then()
                        .statusCode(204));
    }

    @Test
    @DisplayName("6. POST: Негативный тест - ошибка регистрации")
    void registerNegativeTest() {
        UserRegistration data = new UserRegistration();
        data.setEmail("sydney@fife"); // Без пароля

        EmailResponseError response = step("Попытка регистрации без пароля", () ->
                given(reqresRequestSpec)
                        .body(data)
                        .when()
                        .post("/register")
                        .then()
                        .statusCode(400)
                        .extract().as(EmailResponseError.class));

        step("Проверить текст ошибки", () ->
                assertThat(response.getError()).isEqualTo("Missing password"));
    }
}
