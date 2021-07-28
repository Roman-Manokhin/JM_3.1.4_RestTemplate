package ru.rmanokhin.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext springContext = SpringApplication.run(Application.class, args);

        RestTemplateCRUD restTemplateCRUD = springContext.getBean("restTemplateCRUD", RestTemplateCRUD.class);

        System.out.println(restTemplateCRUD.answer());
    }

}
