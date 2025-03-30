package ru.ilyacherney.flowers.bouquet;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.ilyacherney.flowers.flower.Flower;

import java.util.List;

@Service
public class BouquetService {

    private final BouquetRepository bouquetRepository;
    private Bouquet activeBouquet = null;

    public BouquetService(BouquetRepository bouquetRepository) {
        this.bouquetRepository = bouquetRepository;
    }

    @Transactional
    public Bouquet createBouquet() {
        Bouquet newBouquet = new Bouquet();
        activeBouquet = bouquetRepository.save(newBouquet);
        return activeBouquet;
    }

    @Transactional
    public void addFlowersToBouquet(List<Flower> flowers) {
        if (activeBouquet == null) {
            activeBouquet = createBouquet();
        }
        flowers.forEach(flower -> flower.setBouquet(activeBouquet));
        activeBouquet.getFlowers().addAll(flowers); // Use addAll instead of setFlowers
        bouquetRepository.save(activeBouquet);
    }

    public List<Bouquet> getAllBouquets() {
        return (List<Bouquet>) bouquetRepository.findAll();
    }


    public Bouquet getActiveBouquet() {
        return activeBouquet;
    }

    public Long getActiveBouquetId() {
        return activeBouquet != null ? activeBouquet.getId() : null;
    }

}
