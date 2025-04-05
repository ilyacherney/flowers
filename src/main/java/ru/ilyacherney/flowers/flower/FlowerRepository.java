package ru.ilyacherney.flowers.flower;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FlowerRepository extends JpaRepository<Flower, Long> {
    Flower save(Flower flower);
    void deleteById(Long id);
    boolean existsById(Long id);
    boolean existsByCultivarId(Long id);

    @Query(value = "SELECT * FROM flowers f WHERE f.cultivar_id = :cultivarId AND f.bouquet_id IS NULL LIMIT 1", nativeQuery = true)
    Optional<Flower> findFirstByCultivarIdAndBouquetIsNull(Long cultivarId);

    @Query(value = "SELECT * FROM flowers f WHERE f.cultivar_id = :cultivarId AND f.bouquet_id = :bouquetId LIMIT 1", nativeQuery = true)
    Optional<Flower> findFirstByCultivarIdAndBouquetId(Long cultivarId, Long bouquetId);

    List<Flower> findAllByBouquetId(Long bouquetId);

    int countByCultivarIdAndBouquetIdIsNull(Long cultivarId);
}
