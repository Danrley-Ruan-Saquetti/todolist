package com.esliph.todolist;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.esliph.todolist.modules.auth.Path;
import com.esliph.todolist.modules.auth.PathsWithAuthorization;
import com.esliph.todolist.modules.auth.decorator.Authorization;

@SpringBootApplication
public class TodolistApplication {

	static ConfigurableApplicationContext app;

	// mvn spring-boot:run
	public static void main(String[] args) {
		TodolistApplication.app = SpringApplication.run(TodolistApplication.class, args);

		System.out.println("INFO: Server started!");

		// PutMapping
		// GetMapping
		// PostMapping
		// PatchMapping
		// DeleteMapping
		var controllers = getControllers();

		controllers.forEach(controller -> {
			var annotationClass = getAnnotationOfClass(controller, RequestMapping.class);

			if (annotationClass == null) {
				return;
			}

			var methods = getMethodsHasAnnotation(controller, Authorization.class);

			ArrayList<Path> paths = new ArrayList<>();

			for (var method : methods) {
				var annotationPutMapping = getAnnotationOfMethod(method, PutMapping.class);
				var annotationGetMapping = getAnnotationOfMethod(method, GetMapping.class);
				var annotationPostMapping = getAnnotationOfMethod(method, PostMapping.class);
				var annotationPatchMapping = getAnnotationOfMethod(method, PatchMapping.class);
				var annotationDeleteMapping = getAnnotationOfMethod(method, DeleteMapping.class);

				if (annotationPutMapping != null) {
					paths.add(new Path(RequestMethod.PUT, annotationClass.value(), annotationPutMapping.value()));
				}
				if (annotationGetMapping != null) {
					paths.add(new Path(RequestMethod.GET, annotationClass.value(), annotationGetMapping.value()));
				}
				if (annotationPostMapping != null) {
					paths.add(new Path(RequestMethod.POST, annotationClass.value(), annotationPostMapping.value()));
				}
				if (annotationPatchMapping != null) {
					paths.add(new Path(RequestMethod.PATCH, annotationClass.value(), annotationPatchMapping.value()));
				}
				if (annotationDeleteMapping != null) {
					paths.add(new Path(RequestMethod.DELETE, annotationClass.value(), annotationDeleteMapping.value()));
				}
			}

			PathsWithAuthorization.addPaths(paths);
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

	public static ArrayList<Method> getMethodsHasAnnotation(Class<?> _class, Class<? extends Annotation> annotation) {
		var methods = new ArrayList<Method>();

		var methodsOfClass = _class.getDeclaredMethods();

		for (int i = 0; i < methodsOfClass.length; i++) {
			var method = methodsOfClass[i];

			var annotationMethod = getAnnotationOfMethod(method, annotation);

			if (annotationMethod == null) {
				continue;
			}

			methods.add(method);
		}

		return methods;
	}

	public static <T extends Annotation> T getAnnotationOfClass(Class<?> _class, Class<T> annotation) {
		var annotationClass = _class.getAnnotation(annotation);

		return annotationClass;
	}

	public static <T extends Annotation> T getAnnotationOfMethod(Method method, Class<T> annotation) {
		var annotationMethod = method.getAnnotation(annotation);

		return annotationMethod;
	}
}
