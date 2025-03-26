package ru.ilyacherney.flowers.bouquet;

import org.springframework.stereotype.Service;
import ru.ilyacherney.flowers.flower.Flower;

import java.util.List;

@Service
public class BouquetService {

    private final BouquetRepository bouquetRepository;

    public BouquetService(BouquetRepository bouquetRepository) {
        this.bouquetRepository = bouquetRepository;
    }

    public Bouquet createBouquet() {
        return bouquetRepository.save(new Bouquet());
    }

    public void addFlowersToBouquet(List<Flower> flowers, Bouquet bouquet) {
        flowers.forEach(flower -> flower.setBouquet(bouquet));
        bouquet.setFlowers(flowers);
        bouquetRepository.save(bouquet);
    }

    public List<Bouquet> getAllBouquets() {
        return (List<Bouquet>) bouquetRepository.findAll();
    }

}
