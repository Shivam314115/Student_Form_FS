package com.example.StudentForm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;



@SpringBootApplication
// shorthand for @Configuration, @EnableAutoConfiguration, and @ComponentScan annotations.
@EnableJpaRepositories(basePackages = "com.example.StudentForm.repository")

//main class to run the spring boot application.

public class StudentFormApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentFormApplication.class, args);
	}

}
