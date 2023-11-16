package ru.katkova;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;

public abstract class TestsBase
{
    @BeforeEach
    public void setUp()
    {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }
}
