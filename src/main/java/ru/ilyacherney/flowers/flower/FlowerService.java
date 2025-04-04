package ru.ilyacherney.flowers.flower;

import org.springframework.stereotype.Service;
import ru.ilyacherney.flowers.cultivar.Cultivar;

@Service
public class FlowerService {

    private final FlowerRepository flowerRepository;

    public FlowerService(FlowerRepository flowerRepository) {
        this.flowerRepository = flowerRepository;
    }

    public Flower createFlower(Cultivar cultivar) {
        return flowerRepository.save(new Flower(cultivar));
    }

    public void deleteFlower(Long id) {
        if (flowerRepository.existsById(id)) {
            flowerRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Flower with ID " + id + " not found.");
        }
    }

    public Flower findAvailableFlowerByCultivarId(Long cultivarId) {
        return flowerRepository.findFirstByCultivarIdAndBouquetIsNull(cultivarId)
                .orElseThrow(() -> new IllegalStateException("No available flowers for cultivar ID " + cultivarId));
    }
}
