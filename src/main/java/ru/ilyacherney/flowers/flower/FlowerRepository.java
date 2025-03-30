package ru.ilyacherney.flowers.flower;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FlowerRepository extends JpaRepository<Flower, Long> {
    Flower save(Flower flower);
    void deleteById(Long id);
    boolean existsById(Long id);
    boolean existsByCultivarId(Long id);

    @Query("SELECT f FROM Flower f WHERE f.cultivar.id = :cultivarId AND f.bouquet.id IS NULL")
    Optional<Flower> findFirstByCultivarIdAndBouquetIsNull(Long cultivarId);
}
