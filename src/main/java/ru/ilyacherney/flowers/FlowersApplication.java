package ru.ilyacherney.flowers;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.ilyacherney.flowers.cultivar.Cultivar;
import ru.ilyacherney.flowers.cultivar.CultivarService;
import ru.ilyacherney.flowers.flower.Flower;
import ru.ilyacherney.flowers.flower.FlowerService;

import java.math.BigDecimal;

@SpringBootApplication
public class FlowersApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlowersApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(CultivarService cultivarService, FlowerService flowerService) {
        return args -> {
            Cultivar cultivar = cultivarService.createCultivar("Одуванчик", new BigDecimal(15.00));
            Flower flower = flowerService.createFlower(cultivar);
            System.out.println(flower);
            System.out.println(cultivar);
        };
    }
}
