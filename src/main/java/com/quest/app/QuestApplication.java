package com.quest.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QuestApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuestApplication.class, args);
        System.out.println("\n=======================================================");
        System.out.println(" 🚀 QUEST Application is running successfully!");
        System.out.println(" URL: http://localhost:8080");
        System.out.println(" Tagline: \"Your Journey Toward a Goal\"");
        System.out.println("=======================================================\n");
    }
}
