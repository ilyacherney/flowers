package ru.ilyacherney.flowers.flower;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlowerRepository extends JpaRepository<Flower, Long> {
    Flower save(Flower flower);
    void deleteById(Long id);
    boolean existsById(Long id);
    boolean existsByCultivarId(Long id);
}
