package ru.ilyacherney.flowers.cultivar;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CultivarRepository extends JpaRepository<Cultivar, Long> {
    public Cultivar save(Cultivar cultivar);
    public boolean existsById(Long id);
}
