package ru.katkova;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import ru.katkova.pojo.*;

import java.util.List;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

public class OrderTest
{
    @BeforeEach
    public void setUp()
    {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    public static Stream<Arguments> orderCreateColors()
    {
        return Stream.of(
                Arguments.of(List.of("BLACK")),
                Arguments.of(List.of("GREY")),
                Arguments.of(List.of("BLACK", "GREY")),
                Arguments.of(List.of())
        );
    }

    @ParameterizedTest
    @MethodSource("orderCreateColors")
    public void createOrderSuccessful(List<String> colors)
    {
        given()
                .when()
                .contentType(ContentType.JSON)
                .body(new CreateOrderParameters(
                        "Naruto",
                        "Uchiha",
                        "Konoha, 142 apt.",
                        "4",
                        "+7 800 355 35 35",
                        5L,
                        "2020-06-06",
                        "Saske, come back to Konoha",
                        colors
                ))
                .post("/api/v1/orders")
                .then()
                .statusCode(201)
                .and()
                .body("track", notNullValue());
    }

    @Test
    public void listOrdersSuccessful()
    {
        given()
                .when()
                .get("/api/v1/orders")
                .then()
                .statusCode(200)
                .and()
                .body("orders", notNullValue());
    }
}
