package com.upgrade.pacificocean;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
@EnableAsync
public class PacificOceanApplication {

	public static void main(String[] args) {
		SpringApplication.run(PacificOceanApplication.class, args);
	}
}
