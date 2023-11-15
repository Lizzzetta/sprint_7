package ru.katkova;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import ru.katkova.pojo.*;

import java.util.Random;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CourierTest
{
    @BeforeEach
    public void setUp()
    {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Test
    public void createCourierSuccessful()
    {
        String login = "katkova_test_login_" + new Random().nextInt();
        String password = "katkova_test_password";

        given()
                .when()
                .contentType(ContentType.JSON)
                .body(new CourierCreateParameters(login, password, "test_firstname"))
                .post("/api/v1/courier")
                .then()
                .statusCode(201)
                .and()
                .body("ok", is(true));
    }

    @Test
    public void loginIsRequiredForCreateCourier()
    {
        given()
                .when()
                .contentType(ContentType.JSON)
                .body(new CourierCreateParameters(null, "katkova_test_password", "test_firstname"))
                .post("/api/v1/courier")
                .then()
                .statusCode(400)
                .and()
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }

    @Test
    public void passwordIsRequiredForCreateCourier()
    {
        given()
                .when()
                .contentType(ContentType.JSON)
                .body(new CourierCreateParameters("katkova_test_login", null, "test_firstname"))
                .post("/api/v1/courier")
                .then()
                .statusCode(400)
                .and()
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }

    @Test
    public void createCourierWithSameLoginIsForbidden()
    {
        String login = "katkova_test_login_" + new Random().nextInt();
        String password = "katkova_test_password";

        given()
                .when()
                .contentType(ContentType.JSON)
                .body(new CourierCreateParameters(login, password, "test_firstname"))
                .post("/api/v1/courier")
                .then()
                .statusCode(201);

        given()
                .when()
                .contentType(ContentType.JSON)
                .body(new CourierCreateParameters(login, password, "test_firstname"))
                .post("/api/v1/courier")
                .then()
                .statusCode(409)
                .and()
                .body("message", is("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    public void courierLoginSuccessful()
    {
        String login = "katkova_test_login_" + new Random().nextInt();
        String password = "katkova_test_password";

        given()
                .when()
                .contentType(ContentType.JSON)
                .body(new CourierCreateParameters(login, password, "test_firstname"))
                .post("/api/v1/courier")
                .then()
                .statusCode(201);

        given()
                .when()
                .contentType(ContentType.JSON)
                .body(new CourierLoginParameters(login, password))
                .post("/api/v1/courier/login")
                .then()
                .statusCode(200)
                .and()
                .body("id", notNullValue());
    }

    @Test
    public void loginRequiredForCourierLogin()
    {
        given()
                .when()
                .contentType(ContentType.JSON)
                .body(new CourierLoginParameters(null, "katkova_test_password"))
                .post("/api/v1/courier/login")
                .then()
                .statusCode(400)
                .and()
                .body("message", is("Недостаточно данных для входа"));
    }

    @Test
    @Disabled // request hangs
    public void passwordRequiredForCourierLogin()
    {
        given()
                .when()
                .contentType(ContentType.JSON)
                .body(new CourierLoginParameters("katkova_test_login", null))
                .post("/api/v1/courier/login")
                .then()
                .statusCode(400)
                .and()
                .body("message", is("Недостаточно данных для входа"));
    }

    @Test
    public void courierLoginWithWrongLoginIfFail()
    {
        String login = "katkova_test_login_" + new Random().nextInt();

        given()
                .when()
                .contentType(ContentType.JSON)
                .body(new CourierLoginParameters(login, "katkova_test_password"))
                .post("/api/v1/courier/login")
                .then()
                .statusCode(404)
                .and()
                .body("message", is("Учетная запись не найдена"));
    }

    @Test
    public void courierLoginWithWrongPasswordIfFail()
    {
        String login = "katkova_test_login_" + new Random().nextInt();
        String password = "katkova_test_password";

        given()
                .when()
                .contentType(ContentType.JSON)
                .body(new CourierCreateParameters(login, password, "test_firstname"))
                .post("/api/v1/courier")
                .then()
                .statusCode(201);

        given()
                .when()
                .contentType(ContentType.JSON)
                .body(new CourierLoginParameters(login, password + "_wrong"))
                .post("/api/v1/courier/login")
                .then()
                .statusCode(404)
                .and()
                .body("message", is("Учетная запись не найдена"));
    }
}
