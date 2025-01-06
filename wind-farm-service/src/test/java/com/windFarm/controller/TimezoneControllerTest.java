package com.windFarm.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import java.time.ZoneId;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TimezoneControllerTest {

    @LocalServerPort
    private Integer port;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
    }
    @Test
    void shouldGetAllTimezones() {
        Set<String> timezones = ZoneId.getAvailableZoneIds().stream().sorted().collect(Collectors.toCollection(LinkedHashSet::new));

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/timezones")
                .then()
                .statusCode(200)
                .body(".", hasSize(timezones.size()));
    }
}