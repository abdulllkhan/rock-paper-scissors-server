package com.project.stone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.project.stone", "com.project.stone.hello"})
public class StoneApplication {

	public static void main(String[] args) {
		SpringApplication.run(StoneApplication.class, args);
	}

}

// /Users/abdulsamadkhan/Documents/study/java/new trial/stone/src/main/java/com/project/stone/StoneApplication.java