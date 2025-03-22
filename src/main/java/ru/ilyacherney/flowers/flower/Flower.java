package ru.ilyacherney.flowers.flower;

import jakarta.persistence.*;


@Entity
@Table(name = "flowers")
public class Flower {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
}
