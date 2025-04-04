package ru.ilyacherney.flowers.sales;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.ilyacherney.flowers.bouquet.Bouquet;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class SaleService {

    private final SaleRepository saleRepository;

    public SaleService(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    @Transactional
    public void sale(Bouquet bouquet) {
        Sale sale = new Sale();
        sale.setPrice(new BigDecimal(100));
        sale.setBouquetId(bouquet.getId());
        sale.setSoldAt(LocalDateTime.now());
        saleRepository.save(sale);
    }
}
