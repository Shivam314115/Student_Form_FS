package com.example.StudentForm.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
// learnt something new here.

//CORS is basically a security feature implemented by web browsers to prevent malicious websites 
// from making requests to a different domain than the one that served the web page. 

// this annotation is used to mark  a configuration class in Spring.
// basically configuration == beans, settings, and other configurations that are used to set up the application context.
// this class implements the WebMvcConfigurer interface, 
// which allows us to customize the configuration of Spring MVC.

public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")// it means CORS config all paths in the application.
                .allowedOrigins("http://localhost:5173")// this is the origin that is allowed to make requests to our backend.
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")//methods allowed through CORS.
                .allowedHeaders("*")// any request header is allpowed.
                .allowCredentials(true)// idk what this does, but it allows cookies to be sent with the request.
                .maxAge(3600); //  the maximum time (in seconds) that the browser should cache the  response to the automated request made by brwoser.
    }
}