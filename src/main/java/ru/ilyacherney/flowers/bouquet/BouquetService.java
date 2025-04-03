package ru.ilyacherney.flowers.bouquet;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.ilyacherney.flowers.flower.Flower;
import ru.ilyacherney.flowers.flower.FlowerRepository;

import java.util.List;

@Service
public class BouquetService {

    private final BouquetRepository bouquetRepository;
    private final FlowerRepository flowerRepository;
    private Bouquet activeBouquet = null;

    public BouquetService(BouquetRepository bouquetRepository, FlowerRepository flowerRepository) {
        this.bouquetRepository = bouquetRepository;
        this.flowerRepository = flowerRepository;
    }

    @Transactional
    public Bouquet createBouquet() {
        Bouquet newBouquet = new Bouquet();
        activeBouquet = bouquetRepository.save(newBouquet);
        return activeBouquet;
    }

    @Transactional
    public void addFlowerToBouquet(long cultivarId) {
        if (activeBouquet == null) {
            activeBouquet = createBouquet();
        }
        Flower flower = flowerRepository.findFirstByCultivarIdAndBouquetIsNull(cultivarId)
                .orElseThrow(() -> new RuntimeException("Flower with no bouquet and cultivar id = " + cultivarId + " not found"));

        flower.setBouquet(activeBouquet);
        flowerRepository.save(flower); // Сохраняем только flower, Hibernate сам обновит связь
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
