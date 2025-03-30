package ru.ilyacherney.flowers.cultivar;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ilyacherney.flowers.flower.Flower;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cultivars")
@Data
@NoArgsConstructor
public class Cultivar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private BigDecimal price;

    @OneToMany(mappedBy = "cultivar", cascade = CascadeType.ALL, orphanRemoval = false)
    private Set<Flower> flowers = new HashSet<>();

    public Cultivar(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }
}
