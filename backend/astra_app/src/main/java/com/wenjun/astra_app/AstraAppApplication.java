package com.wenjun.astra_app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.wenjun.astra_app", "com.wenjun.astra_persistence"})
@MapperScan(basePackages = "com.wenjun.astra_persistence")
public class AstraAppApplication {
	public static void main(String[] args) {
		SpringApplication.run(AstraAppApplication.class, args);
	}
}
