package ru.ilyacherney.flowers.bouquet;

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

    public Bouquet createBouquet() {
        Bouquet newBouquet = new Bouquet();
        activeBouquet = bouquetRepository.save(newBouquet);  // Сохраняем новый букет
        return activeBouquet;
    }

    public void addFlowersToBouquet(List<Flower> flowers) {
        if (activeBouquet == null) {
            activeBouquet = createBouquet();  // Если нет активного букета, создаём новый
        }

        flowers.forEach(flower -> flower.setBouquet(activeBouquet));  // Устанавливаем букет для каждого цветка
        activeBouquet.setFlowers(flowers);
        bouquetRepository.save(activeBouquet);  // Сохраняем букет с добавленными цветами
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
