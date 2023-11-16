package ru.katkova.client;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.*;
import ru.katkova.pojo.*;

import static io.restassured.RestAssured.given;

public class CourierClient
{
    public static final String CREATE_COURIER_PATH = "/api/v1/courier";
    public static final String LOGIN_COURIER_PATH = "/api/v1/courier/login";
    public static final String DELETE_COURIER_PATH = "/api/v1/courier/%d";

    @Step("Create courier")
    public Response createCourier(String login, String password, String firstname)
    {
        return given()
                .when()
                .contentType(ContentType.JSON)
                .body(new CourierCreateParameters(login, password, firstname))
                .post(CREATE_COURIER_PATH);
    }

    @Step("Login courier")
    public Response loginCourier(String login, String password)
    {
        return given()
                .when()
                .contentType(ContentType.JSON)
                .body(new CourierLoginParameters(login, password))
                .post(LOGIN_COURIER_PATH);
    }

    @Step("Delete courier")
    public Response deleteCourier(Long id)
    {
        return given()
                .when()
                .delete(String.format(DELETE_COURIER_PATH, id));
    }
}
