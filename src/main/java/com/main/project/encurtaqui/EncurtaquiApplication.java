package com.main.project.encurtaqui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EncurtaquiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EncurtaquiApplication.class, args);
	}

}
