package ru.ilyacherney.flowers.sales;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import org.springframework.stereotype.Component;
import ru.ilyacherney.flowers.bouquet.BouquetService;
import ru.ilyacherney.flowers.states.State;
import ru.ilyacherney.flowers.states.StateRenderer;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Component
public class SalesState implements State {

    private final SaleService saleService;
    private final StateRenderer renderer;
    private final BouquetService bouquetService;

    public SalesState(SaleService saleService, StateRenderer renderer, BouquetService bouquetService) {
        this.saleService = saleService;
        this.renderer = renderer;
        this.bouquetService = bouquetService;
    }

    @Override
    public void handleUpdate(Update update) {

    }

    @Override
    public void render(long chatId, int editingMessageId) {
        renderer.render((State) this, chatId, editingMessageId);
    }

    @Override
    public String getDisplayName() {
        return "Продажи:";
    }

    @Override
    public InlineKeyboardMarkup getInlineKeyboardMarkup() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm", new Locale("ru"));

        List<Sale> sales = saleService.getAllSales();
        for (Sale sale : sales) {

            InlineKeyboardButton saleButton = new InlineKeyboardButton(sale.getSoldAt().format(formatter)).callbackData(sale.getSoldAt().toString());
            InlineKeyboardButton bouquetName = new InlineKeyboardButton("Букет " + sale.getBouquetId()).callbackData(sale.getSoldAt().toString());
            InlineKeyboardButton price = new InlineKeyboardButton(sale.getPrice() + " руб.").callbackData(sale.getSoldAt().toString());

            inlineKeyboardMarkup.addRow(saleButton, bouquetName, price);
        }
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("« Назад").callbackData("start"));
        return inlineKeyboardMarkup;
    }
}
