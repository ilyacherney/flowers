package ru.ilyacherney.flowers.bouquet;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import org.springframework.stereotype.Component;
import ru.ilyacherney.flowers.sales.SaleService;
import ru.ilyacherney.flowers.states.State;
import ru.ilyacherney.flowers.states.StateRenderer;


@Component
public class BouqetsState implements State {

    private final TelegramBot bot;
    private final StateRenderer renderer;
    private final BouquetService bouquetService;
    private final SaleService saleService;

    private int editingMessageId;

    public BouqetsState(TelegramBot bot, StateRenderer renderer, BouquetService bouquetService, SaleService saleService) {
        this.bot = bot;
        this.renderer = renderer;
        this.bouquetService = bouquetService;
        this.saleService = saleService;
    }

    @Override
    public String getDisplayName() {
        return "Букеты";
    }

    @Override
    public void handleUpdate(Update update) {
        if (update.callbackQuery() == null) return;

        String data = update.callbackQuery().data();
        long chatId = update.callbackQuery().message().chat().id();

        if (data.startsWith("sale:")) {
            long bouquetId = Long.parseLong(data.substring("sale:".length()));
            Bouquet bouquet = bouquetService.getBouquetById(bouquetId);
            saleService.sale(bouquet);
            renderer.render(this, chatId, editingMessageId, update.callbackQuery());
        }

        if (data.startsWith("disassemble:")) {
            long bouquetId = Long.parseLong(data.substring("disassemble:".length()));
            Bouquet bouquet = bouquetService.getBouquetById(bouquetId);
            bouquetService.deleteBouquet(bouquet);
            renderer.render(this, chatId, editingMessageId, update.callbackQuery());
        }
    }

    @Override
    public InlineKeyboardMarkup getInlineKeyboardMarkup() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        bouquetService.getAllBouquets().forEach(bouquet -> {
                InlineKeyboardButton bouquetButton = new InlineKeyboardButton("Букет " + bouquet.getId()).callbackData(Long.toString(bouquet.getId()));
                InlineKeyboardButton price = new InlineKeyboardButton(bouquetService.calculatePrice(bouquet) + " руб.").callbackData("цена:" + Long.toString(bouquet.getId()));
                InlineKeyboardButton disassemble = new InlineKeyboardButton("Разобрать").callbackData("disassemble:" + Long.toString(bouquet.getId()));
                InlineKeyboardButton saleButton = new InlineKeyboardButton("Продать").callbackData("sale:" + Long.toString(bouquet.getId()));
                inlineKeyboardMarkup.addRow(bouquetButton, price, disassemble, saleButton);
            }
        );

        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("+ Создать").callbackData("new_bouquet"), new InlineKeyboardButton(" ").callbackData("1"), new InlineKeyboardButton(" ").callbackData("2"), new InlineKeyboardButton(" ").callbackData("3"));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("« Назад").callbackData("start"));
        return inlineKeyboardMarkup;
    }

    @Override
    public void render(long chatId, int editingMessageId) {
        this.editingMessageId = editingMessageId;
        renderer.render((State) this, chatId, editingMessageId);
    }

}
