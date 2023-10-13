package com.esliph.todolist;

import java.lang.annotation.Annotation;
import java.util.ArrayList;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;

import com.esliph.todolist.modules.auth.decorator.Authorization;
import com.esliph.todolist.modules.auth.decorator.PathsWithAuthorization;

@SpringBootApplication
public class TodolistApplication {

	static ConfigurableApplicationContext app;

	// mvn spring-boot:run
	public static void main(String[] args) {
		TodolistApplication.app = SpringApplication.run(TodolistApplication.class, args);

		var controllers = getControllers();

		var annotationsAuthorization = getAnnotations(controllers, Authorization.class);

		annotationsAuthorization.forEach(annotation -> {
			PathsWithAuthorization.addPath(annotation.path());
		});
	}

	private static ArrayList<Class<?>> getControllers() {
		String[] controllerNames = app
				.getBeanNamesForAnnotation(Controller.class);

		ArrayList<Class<?>> controllers = new ArrayList<>();

		for (String controllerName : controllerNames) {
			Class<?> controllerClass = app.getType(controllerName);

			controllers.add(controllerClass);
		}

		return controllers;
	}

	private static <T extends Annotation> ArrayList<T> getAnnotations(ArrayList<Class<?>> classes,
			Class<T> annotation) {
		ArrayList<T> annotations = new ArrayList<>();

		classes.forEach(controller -> {
			var methods = controller.getDeclaredMethods();

			for (int i = 0; i < methods.length; i++) {
				var method = methods[i];

				var annotationMethod = method.getAnnotation(annotation);

				if (annotationMethod == null) {
					return;
				}

				annotations.add(annotationMethod);
			}
		});

		return annotations;
	}
}
