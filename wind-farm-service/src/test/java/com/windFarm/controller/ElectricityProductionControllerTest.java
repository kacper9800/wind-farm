package com.windFarm.controller;

import static io.restassured.RestAssured.given;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.hasSize;

import com.windFarm.dto.ElectricityProductionDto;
import com.windFarm.entity.ElectricityProduction;
import com.windFarm.entity.WindFarm;
import com.windFarm.repository.ElectricityProductionRepository;
import com.windFarm.repository.WindFarmRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import jakarta.transaction.Transactional;
import org.junit.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import static org.hamcrest.Matchers.equalTo;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ElectricityProductionControllerTest {

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
        electricityProductionRepository.deleteAll();
        windFarmRepository.deleteAll();

        WindFarm windFarm = WindFarm.builder()
                .capacityMw(10D)
                .location("54.28 12.22")
                .timezone("Europe/Berlin")
                .description("Wind farm in Germany").build();

        windFarmRepository.save(windFarm);
    }


    @Test
    void shouldGetAllElectricityProduction() {
        WindFarm createdWindFarm = windFarmRepository.findAll().get(0);

        ElectricityProduction electricityProduction1 = ElectricityProduction.builder()
                .electricityProducedMW(10D)
                .timestamp(LocalDateTime.of(2025, 1, 1, 10, 10, 10))
                .windFarm(createdWindFarm)
                .build();

        ElectricityProduction electricityProduction2 = ElectricityProduction.builder()
                .electricityProducedMW(10D)
                .timestamp(LocalDateTime.of(2025, 1, 1, 11, 10, 10))
                .windFarm(createdWindFarm)
                .build();

        List<ElectricityProduction> electricityProductions = List.of(
                electricityProduction1, electricityProduction2
        );
        electricityProductionRepository.saveAll(electricityProductions);

        given()
                .contentType(ContentType.JSON)
                .when()
                .param("windFarmId", createdWindFarm.getId())
                .get("/api/electricityProductions")
                .then()
                .statusCode(200)
                .body("content", hasSize(2));
    }

    @Test
    void shouldCreateElectricityProduction() {
        WindFarm createdWindFarm = windFarmRepository.findAll().get(0); // Pobierz pierwszy zapisany obiekt

        ElectricityProductionDto dto = ElectricityProductionDto.builder()
                .electricityProducedMw(10D)
                .timestamp(LocalDateTime.of(2025, 1, 1, 10, 10, 10).toString())
                .windFarmId(createdWindFarm.getId())
                .build();

        given()
                .contentType(ContentType.JSON)
                .body(dto)
                .when()
                .post("/api/electricityProductions")
                .then()
                .statusCode(201)
                .body("electricity_produced_mw", equalTo(10.0F))
                .body("timestamp", equalTo("2025-01-01T10:10:10"))
                .body("wind_farm_id", equalTo(createdWindFarm.getId().intValue()));
    }
}