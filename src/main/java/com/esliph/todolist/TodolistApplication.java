package com.esliph.todolist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.esliph.todolist.services.AnnotationController;

@SpringBootApplication
public class TodolistApplication {

	public static ConfigurableApplicationContext app;

	// mvn spring-boot:run
	public static void main(String[] args) {
		TodolistApplication.app = SpringApplication.run(TodolistApplication.class, args);

		AnnotationController.initComponents();

		System.out.println("INFO: Server started!");
	}
}
