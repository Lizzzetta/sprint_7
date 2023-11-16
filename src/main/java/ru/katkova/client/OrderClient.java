package ru.katkova.client;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import ru.katkova.pojo.CreateOrderParameters;

import java.util.List;

import static io.restassured.RestAssured.given;

public class OrderClient
{
    public static final String CREATE_ORDER_PATH = "/api/v1/orders";
    public static final String GET_ORDERS_PATH = "/api/v1/orders";

    @Step("Create order")
    public Response createOrder(String firstName, String lastName, String address, String metroStation, String phone, Long rentTime, String deliveryDate, String comment, List<String> color)
    {
        return given()
                .when()
                .contentType(ContentType.JSON)
                .body(new CreateOrderParameters(
                        firstName,
                        lastName,
                        address,
                        metroStation,
                        phone,
                        rentTime,
                        deliveryDate,
                        comment,
                        color
                ))
                .post(CREATE_ORDER_PATH);
    }

    @Step("Get orders")
    public Response getOrders()
    {
        return given()
                .when()
                .get(GET_ORDERS_PATH);
    }
}
