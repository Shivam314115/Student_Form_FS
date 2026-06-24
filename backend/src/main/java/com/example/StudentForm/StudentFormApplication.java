package com.example.StudentForm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;



@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.StudentForm.repository")
public class StudentFormApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentFormApplication.class, args);
	}

}
