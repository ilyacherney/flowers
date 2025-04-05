package ru.ilyacherney.flowers.states;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import org.springframework.stereotype.Component;
import ru.ilyacherney.flowers.cultivar.Cultivar;
import ru.ilyacherney.flowers.sales.Sale;
import ru.ilyacherney.flowers.sales.SaleService;

import java.util.List;

@Component
public class SalesState implements State {

    private final SaleService saleService;
    private final StateRenderer renderer;

    public SalesState(SaleService saleService, StateRenderer renderer) {
        this.saleService = saleService;
        this.renderer = renderer;
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

        List<Sale> sales = saleService.getAllSales();
        for (Sale sale : sales) {
            InlineKeyboardButton saleButton = new InlineKeyboardButton(sale.getSoldAt().toString()).callbackData(sale.getSoldAt().toString());
            inlineKeyboardMarkup.addRow(saleButton);
        }

        return inlineKeyboardMarkup;
    }
}
