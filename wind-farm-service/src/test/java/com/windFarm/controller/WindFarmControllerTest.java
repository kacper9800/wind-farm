package com.windFarm.controller;

import com.windFarm.dto.ElectricityProductionDto;
import com.windFarm.dto.WindFarmDto;
import com.windFarm.entity.ElectricityProduction;
import com.windFarm.entity.WindFarm;
import com.windFarm.repository.ElectricityProductionRepository;
import com.windFarm.repository.WindFarmRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

import java.time.LocalDateTime;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WindFarmControllerTest {

    @LocalServerPort
    private Integer port;

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:16-alpine"
    );

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    WindFarmRepository windFarmRepository;

    @Autowired
    ElectricityProductionRepository electricityProductionRepository;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
        windFarmRepository.deleteAll();
    }

    @Test
    void shouldGetAllWindFarms() {
        WindFarm windFarm = WindFarm.builder()
                .id(null)
                .capacityMw(10D)
                .location("54.28 12.22")
                .timezone("Europe/Berlin")
                .description("Wind farm in Germany").build();

        WindFarm windFarm2 = WindFarm.builder()
                .id(null)
                .capacityMw(8D)
                .location("40.57 -74.07")
                .timezone("America/New_York")
                .description("Wind farm in USA").build();

        windFarmRepository.save(windFarm);
        windFarmRepository.save(windFarm2);

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/windFarms")
                .then()
                .statusCode(200)
                .body("content", hasSize(2));
    }

    @Test
    void shouldCreateWindFarm() {
        WindFarmDto dto = WindFarmDto.builder()
                .capacityMw(10D)
                .location("54.28 12.22")
                .timezone("Europe/Berlin")
                .description("Wind farm in Germany")
                .build();

        given()
                .contentType(ContentType.JSON)
                .body(dto)
                .when()
                .post("/api/windFarms")
                .then()
                .statusCode(201)
                .body("location", equalTo("54.28 12.22"))
                .body("timezone", equalTo("Europe/Berlin"))
                .body("description", equalTo("Wind farm in Germany"));
    }
}