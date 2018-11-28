package com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@MapperScan("com.sensor.*")
@EnableScheduling
@EnableAsync
@SpringBootApplication
public class SensorSysApplication {

	public static void main(String[] args) {

		SpringApplication.run(SensorSysApplication.class, args);
	}
}
