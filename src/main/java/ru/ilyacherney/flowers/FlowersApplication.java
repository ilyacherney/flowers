package ru.ilyacherney.flowers;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.ilyacherney.flowers.bouquet.Bouquet;
import ru.ilyacherney.flowers.bouquet.BouquetService;
import ru.ilyacherney.flowers.cultivar.Cultivar;
import ru.ilyacherney.flowers.cultivar.CultivarService;
import ru.ilyacherney.flowers.flower.Flower;
import ru.ilyacherney.flowers.flower.FlowerService;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class FlowersApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlowersApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(BouquetService bouquetService, FlowerService flowerService, CultivarService cultivarService) {
        return args -> {
//            Bouquet bouquet = bouquetService.createBouquet();
//            System.out.println(bouquet);
//
//            List<Cultivar> cultivars = cultivarService.getAllCultivars();
//
//            Flower flower1 = flowerService.createFlower(cultivars.get(0));
//            Flower flower2 = flowerService.createFlower(cultivars.get(1));
//            Flower flower3 = flowerService.createFlower(cultivars.get(1));
//            List<Flower> flowers = Arrays.asList(flower1, flower2, flower3);
//
//            bouquetService.addFlowersToBouquet(flowers);

        };
    }
}
