package ru.katkova;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import ru.katkova.client.CourierClient;
import ru.katkova.pojo.CourierLoginResponse;

import java.util.Random;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CourierTest extends TestsBase
{
    private final CourierClient courierClient;

    public CourierTest()
    {
        this.courierClient = new CourierClient();
    }

    @Test
    @DisplayName("Check courier create successful")
    public void createCourierSuccessful()
    {
        String login = "katkova_test_login_" + new Random().nextInt();
        String password = "katkova_test_password";

        Response response = courierClient.createCourier(login, password, "test_firstname");
        checkCourierCreateSuccessfulResponse(response);
        cleanUpCreatedCourier(login, password);
    }

    @Step("Check courier create successful response")
    private void checkCourierCreateSuccessfulResponse(Response response)
    {
        response.then()
            .statusCode(201)
            .and()
            .body("ok", is(true));
    }

    @Test
    @DisplayName("Check login parameter is required for courier creation")
    public void loginIsRequiredForCreateCourier()
    {
        Response response = courierClient.createCourier(null, "katkova_test_password", "test_firstname");
        checkCourierNotEnoughRequiredParamsForCreateResponse(response);
    }

    @Step("Check courier not enough required parameters for create response")
    private void checkCourierNotEnoughRequiredParamsForCreateResponse(Response response)
    {
        response
                .then()
                .statusCode(400)
                .and()
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Check password parameter is required for courier creation")
    public void passwordIsRequiredForCreateCourier()
    {
        Response response = courierClient.createCourier("katkova_test_login", null, "test_firstname");
        checkCourierNotEnoughRequiredParamsForCreateResponse(response);
    }

    @Test
    @DisplayName("Check that create courier with same name is forbidden")
    public void createCourierWithSameLoginIsForbidden()
    {
        String login = "katkova_test_login_" + new Random().nextInt();
        String password = "katkova_test_password";

        courierClient.createCourier(login, password, "test_firstname")
                .then()
                .statusCode(201);

        Response response = courierClient.createCourier(login, password, "test_firstname");
        checkCourierCreateForbiddenWithSameName(response);

        cleanUpCreatedCourier(login, password);
    }

    @Step("Check courier create forbidden with same name response")
    private void checkCourierCreateForbiddenWithSameName(Response response)
    {
        response.then()
            .statusCode(409)
            .and()
            .body("message", is("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @DisplayName("Check courier login successful")
    public void courierLoginSuccessful()
    {
        String login = "katkova_test_login_" + new Random().nextInt();
        String password = "katkova_test_password";

        courierClient.createCourier(login, password, "test_firstname")
                .then()
                .statusCode(201);

        Response response = courierClient.loginCourier(login, password);
        checkCourierLoginSuccessfulResponse(response);

        cleanUpCreatedCourier(login, password);
    }

    @Step("Check courier login successful response")
    private void checkCourierLoginSuccessfulResponse(Response response)
    {
        response.then()
                .statusCode(200)
                .and()
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("Check login parameter is required for courier login")
    public void loginRequiredForCourierLogin()
    {
        Response response = courierClient.loginCourier(null, "katkova_test_password");
        checkCourierLoginRequiredParameterResponse(response);
    }

    @Test
    @Disabled // request hangs
    @DisplayName("Check password parameter is required for courier login")
    public void passwordRequiredForCourierLogin()
    {
        Response response = courierClient.loginCourier("katkova_test_login", null);
        checkCourierLoginRequiredParameterResponse(response);
    }

    @Step("Check courier login required parameter response")
    private void checkCourierLoginRequiredParameterResponse(Response response)
    {
        response.then()
                .statusCode(400)
                .and()
                .body("message", is("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Check that courier login with wrong login parameter is fail")
    public void courierLoginWithWrongLoginIfFail()
    {
        String login = "katkova_test_login_" + new Random().nextInt();

        Response response = courierClient.loginCourier(login, "katkova_test_password");
        checkCourierLoginWrongParameterResponse(response);
    }

    @Test
    @DisplayName("Check that courier login with wrong password parameter is fail")
    public void courierLoginWithWrongPasswordIfFail()
    {
        String login = "katkova_test_login_" + new Random().nextInt();
        String password = "katkova_test_password";

        courierClient.createCourier(login, password, "test_firstname")
                .then()
                .statusCode(201);

        Response response = courierClient.loginCourier(login, password + "_wrong");
        checkCourierLoginWrongParameterResponse(response);

        cleanUpCreatedCourier(login, password);
    }

    @Step("Check courier login wrong parameter response")
    private void checkCourierLoginWrongParameterResponse(Response response)
    {
        response.then()
                .statusCode(404)
                .and()
                .body("message", is("Учетная запись не найдена"));
    }

    @Step("Delete test courier")
    private void cleanUpCreatedCourier(String login, String password)
    {
        CourierLoginResponse courierLoginResponse = courierClient.loginCourier(login, password).thenReturn().as(CourierLoginResponse.class);
        courierClient.deleteCourier(courierLoginResponse.getId()).then().statusCode(200);
    }
}
