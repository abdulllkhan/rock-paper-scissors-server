package com.project.stone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@SpringBootApplication
@EnableWebSocket
@ComponentScan(basePackages = {"com.project.stone", "com.project.stone.hello", "com.project.stone.user"})
public class StoneApplication {

	public static void main(String[] args) {
		SpringApplication.run(StoneApplication.class, args);
	}

}

// /Users/abdulsamadkhan/Documents/study/java/new trial/stone/src/main/java/com/project/stone/StoneApplication.java