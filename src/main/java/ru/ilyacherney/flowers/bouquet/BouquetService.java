package ru.ilyacherney.flowers.bouquet;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.ilyacherney.flowers.cultivar.Cultivar;
import ru.ilyacherney.flowers.cultivar.CultivarService;
import ru.ilyacherney.flowers.flower.Flower;
import ru.ilyacherney.flowers.flower.FlowerRepository;
import ru.ilyacherney.flowers.flower.FlowerService;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BouquetService {

    private final BouquetRepository bouquetRepository;
    private final FlowerRepository flowerRepository;
    private final FlowerService flowerService;
    private final CultivarService cultivarService;
    public Bouquet activeBouquet = null;

    public BouquetService(BouquetRepository bouquetRepository, FlowerRepository flowerRepository, FlowerService flowerService, CultivarService cultivarService) {
        this.bouquetRepository = bouquetRepository;
        this.flowerRepository = flowerRepository;
        this.flowerService = flowerService;
        this.cultivarService = cultivarService;
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

    @Transactional
    public void removeFlowerFromBouquet(long cultivarId) {
        if (activeBouquet == null) {
            return;
        }

        Flower flower = flowerRepository.findFirstByCultivarIdAndBouquetId(cultivarId, getActiveBouquetId())
                .orElseThrow(() -> new RuntimeException("Flower with bouquet id = " + getActiveBouquetId() + " and cultivar id = " + cultivarId + " not found"));

        flower.setBouquet(null);
        flowerRepository.save(flower);
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

    public void deleteBouquetAndItsFlowers(Bouquet bouquet) {
        List<Flower> flowers = flowerService.findAllByBouquetId(bouquet.getId());
        for (Flower flower : flowers) {
            flowerService.deleteFlower(flower);
        }
        bouquetRepository.delete(bouquet);
    }

    @Transactional
    public void deleteBouquet(Bouquet bouquet) {
        bouquet = bouquetRepository.findById(bouquet.getId())
                .orElseThrow(() -> new IllegalArgumentException("Букет не найден"));

        // Принудительно инициализируем коллекцию цветов
        bouquet.getFlowers().size();

        for (Flower flower : bouquet.getFlowers()) {
            flower.setBouquet(null);
        }

        bouquet.getFlowers().clear(); // Теперь не вызовет ошибку

        bouquetRepository.delete(bouquet);
    }

    public Bouquet getBouquetById(long id) {
        return bouquetRepository.findById(id).orElse(null);
    }

    public BigDecimal calculatePrice(Bouquet bouquet) {
        BigDecimal price = BigDecimal.ZERO;
        List<Flower> bouquetFlowers = flowerService.findAllByBouquetId(bouquet.getId());
        for (Flower flower : bouquetFlowers) {
            Cultivar cultivar = cultivarService.getCultivarById(flower.getCultivar().getId());
            price = price.add(cultivar.getPrice());
        }
        return price;
    }

}
