package ru.katkova;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import ru.katkova.client.OrderClient;

import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.collection.IsEmptyCollection.empty;

public class OrderTest extends TestsBase
{
    private final OrderClient orderClient;

    public OrderTest()
    {
        this.orderClient = new OrderClient();
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
    @DisplayName("Check order create successful")
    public void createOrderSuccessful(List<String> colors)
    {
        Response response = orderClient.createOrder(
                "Naruto",
                "Uchiha",
                "Konoha, 142 apt.",
                "4",
                "+7 800 355 35 35",
                5L,
                "2020-06-06",
                "Saske, come back to Konoha",
                colors
        );
        checkOrderCreateSuccessfulResponse(response);
    }

    @Step("Check order create successful response")
    private void checkOrderCreateSuccessfulResponse(Response response)
    {
        response.then()
                .statusCode(201)
                .and()
                .body("track", notNullValue());
    }

    @Test
    @DisplayName("Check orders request successful")
    public void listOrdersSuccessful()
    {
        Response response = orderClient.getOrders();
        checkOrdersGetSuccessfulResponse(response);
    }

    @Step("Check orders get successful response")
    private void checkOrdersGetSuccessfulResponse(Response response)
    {
        response.then()
                .statusCode(200)
                .and()
                .body("orders", is(not(empty())));
    }
}
