package ru.ilyacherney.flowers.bouquet;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ilyacherney.flowers.flower.Flower;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bouquets")
@Data
@NoArgsConstructor
public class Bouquet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(mappedBy = "bouquet", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Flower> flowers = new ArrayList<>();

}
