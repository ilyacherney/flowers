package ru.ilyacherney.flowers.sales;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "sales")
@Data
@NoArgsConstructor
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "bouquet_id")
    private long bouquetId;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "sold_at")
    private LocalDateTime soldAt;

}
