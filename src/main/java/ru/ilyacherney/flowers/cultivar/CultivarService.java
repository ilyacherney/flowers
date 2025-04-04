package ru.ilyacherney.flowers.cultivar;

import org.springframework.stereotype.Service;
import ru.ilyacherney.flowers.flower.FlowerRepository;
import ru.ilyacherney.flowers.flower.FlowerService;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CultivarService {

    private final CultivarRepository cultivarRepository;
    private final FlowerRepository flowerRepository;

    public CultivarService(CultivarRepository cultivarRepository, FlowerRepository flowerRepository) {
        this.cultivarRepository = cultivarRepository;
        this.flowerRepository = flowerRepository;
    }

    public Cultivar createCultivar(String name, BigDecimal price) {
        return cultivarRepository.save(new Cultivar(name, price));
    }

    public void deleteCultivar(Long id) {
        if (!cultivarRepository.existsById(id)) {
            throw new IllegalArgumentException("Cultivar with id " + id + " does not exist");
        }
        if (flowerRepository.existsByCultivarId(id)) {
            throw new IllegalStateException("Cannot delete cultivar with id " + id + " because there are flowers associated with this cultivar");
        }
        cultivarRepository.deleteById(id);
    }

    public List<Cultivar> getAllCultivars() {
        return cultivarRepository.findAll();
    }

    public Cultivar getCultivarById(Long id) {
        return cultivarRepository.findById(id).get();
    }
}
