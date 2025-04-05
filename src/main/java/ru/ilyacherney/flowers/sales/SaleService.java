package ru.ilyacherney.flowers.sales;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.ilyacherney.flowers.bouquet.Bouquet;
import ru.ilyacherney.flowers.bouquet.BouquetRepository;
import ru.ilyacherney.flowers.bouquet.BouquetService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SaleService {

    private final SaleRepository saleRepository;
    private final BouquetService bouquetService;

    public SaleService(SaleRepository saleRepository, BouquetService bouquetService) {
        this.saleRepository = saleRepository;
        this.bouquetService = bouquetService;
    }

    @Transactional
    public void sale(Bouquet bouquet) {
        createSale(bouquet);
        bouquetService.deleteBouquet(bouquet);
    }

    private void createSale(Bouquet bouquet) {
        Sale sale = new Sale();
        sale.setPrice(new BigDecimal(100));
        sale.setBouquetId(bouquet.getId());
        sale.setSoldAt(LocalDateTime.now());
        saleRepository.save(sale);
    }


    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }
}
