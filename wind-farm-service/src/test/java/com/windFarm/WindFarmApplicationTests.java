package com.windFarm;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest
@ActiveProfiles("test")
class WindFarmApplicationTests {

	static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgresql:9.6.12")
			.withDatabaseName("testdb")
			.withUsername("testuser")
			.withPassword("testpass");

	static {
		postgreSQLContainer.start();
		System.setProperty("DB_URL", postgreSQLContainer.getJdbcUrl());
		System.setProperty("DB_USERNAME", postgreSQLContainer.getUsername());
		System.setProperty("DB_PASSWORD", postgreSQLContainer.getPassword());
	}


	@Test
	void contextLoads() {
	}

}
