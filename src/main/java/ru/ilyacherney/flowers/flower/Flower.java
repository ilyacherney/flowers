package ru.ilyacherney.flowers.flower;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ilyacherney.flowers.cultivar.Cultivar;


@Entity
@Table(name = "flowers")
@Data
@NoArgsConstructor
public class Flower {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cultivar_id", nullable = false)
    private Cultivar cultivar;

    public Flower(Cultivar cultivar) {
        this.cultivar = cultivar;
    }

}
