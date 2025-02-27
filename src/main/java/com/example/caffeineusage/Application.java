package com.example.caffeineusage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements ApplicationRunner {

    @Autowired
    UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        System.out.println("Start to get user...");
        userService.getUser(1);
        userService.getUser(2);
        userService.getUser(3);
        System.out.println("Get user again...");
        userService.getUser(1);
        userService.getUser(2);
        userService.getUser(3);
        System.out.println("End.");
    }
}