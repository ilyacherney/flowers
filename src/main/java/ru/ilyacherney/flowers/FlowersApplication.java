package ru.ilyacherney.flowers;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FlowersApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlowersApplication.class, args);
    }

    @Bean
    CommandLineRunner runner() {
        return args -> {
        };
    }
}
