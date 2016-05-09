package com.sila;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("/com/sila/")
@SpringBootApplication
public class ServerApplication {

	public static void main(final String[] args) {
		final ApplicationContext ctx = SpringApplication.run(
				ServerApplication.class, args);

		System.out.println("Let's inspect the beans provided by Spring Boot:");

		final String[] beanNames = ctx.getBeanDefinitionNames();
		Arrays.sort(beanNames);
		for (final String beanName : beanNames) {
			System.out.println(beanName);
		}
	}

}
