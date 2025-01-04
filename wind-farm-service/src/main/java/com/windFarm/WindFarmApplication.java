package com.windFarm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WindFarmApplication {

	public static void main(String[] args) {
		SpringApplication.run(WindFarmApplication.class, args);
	}

}


