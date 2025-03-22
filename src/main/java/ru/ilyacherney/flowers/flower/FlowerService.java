package ru.ilyacherney.flowers.flower;

import org.springframework.stereotype.Service;

@Service
public class FlowerService {

    private final FlowerRepository flowerRepository;

    public FlowerService(FlowerRepository flowerRepository) {
        this.flowerRepository = flowerRepository;
    }

    public Flower createFlower() {
        return flowerRepository.save(new Flower());
    }

    public void deleteFlower(Long id) {
        if (flowerRepository.existsById(id)) {
            flowerRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Flower with ID " + id + " not found.");
        }
    }
}
