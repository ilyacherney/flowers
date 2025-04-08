package ru.ilyacherney.flowers.bouquet;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;
import ru.ilyacherney.flowers.cultivar.Cultivar;
import ru.ilyacherney.flowers.cultivar.CultivarService;
import ru.ilyacherney.flowers.flower.FlowerService;
import ru.ilyacherney.flowers.states.State;
import ru.ilyacherney.flowers.states.StateRenderer;

import java.util.List;

@Transactional
@Component
public class NewBouquetState implements State {

    private final StateRenderer stateRenderer;
    private final CultivarService cultivarService;
    private final FlowerService flowerService;
    private final BouquetService bouquetService;
    private final StateRenderer renderer;

    private int editingMessageId;

    public NewBouquetState(StateRenderer stateRenderer, CultivarService cultivarService, FlowerService flowerService, BouquetService bouquetService, StateRenderer renderer) {
        this.stateRenderer = stateRenderer;
        this.cultivarService = cultivarService;
        this.flowerService = flowerService;
        this.bouquetService = bouquetService;
        this.renderer = renderer;
    }

    @Override
    public void handleUpdate(Update update) {
        String data = update.callbackQuery().data();

        long chatId = update.callbackQuery().message().chat().id();

        if (data.startsWith("add_flower_of_cultivar_id:")) {
            long cultivarId = Long.parseLong(data.substring("add_flower_of_cultivar_id:".length()));
            bouquetService.addFlowerToBouquet(cultivarId);
        }

        if (data.startsWith("remove_flower_of_cultivar_id:")) {
            long cultivarId = Long.parseLong(data.substring("remove_flower_of_cultivar_id:".length()));
            bouquetService.removeFlowerFromBouquet(cultivarId);
        }
        renderer.render(this, chatId, editingMessageId, update.callbackQuery());

    }

    @Override
    public void render(long chatId, int editingMessageId) {
        bouquetService.createBouquet();
        stateRenderer.render(this, chatId, editingMessageId);
        this.editingMessageId = editingMessageId;
    }

    @Override
    public String getDisplayName() {
        return "Выберете цветы для нового букета";
    }


    @Override
    public InlineKeyboardMarkup getInlineKeyboardMarkup() {

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<Cultivar> cultivars = cultivarService.getAllCultivars();
        for (Cultivar cultivar : cultivars) {
            InlineKeyboardButton cultivarButton = new InlineKeyboardButton(cultivar.getName()).callbackData(cultivar.getName());
            InlineKeyboardButton minusButton = new InlineKeyboardButton("-").callbackData("remove_flower_of_cultivar_id:" + cultivar.getId());
            InlineKeyboardButton countButton = new InlineKeyboardButton(String.valueOf(flowerService.countByBouquetIdAndCultivarId(bouquetService.getActiveBouquetId(), cultivar.getId())) + " шт.").callbackData(String.valueOf(cultivar.getId()));
            InlineKeyboardButton plusButton = new InlineKeyboardButton("+").callbackData("add_flower_of_cultivar_id:" + cultivar.getId());
            InlineKeyboardButton priceButton = new InlineKeyboardButton(cultivar.getPrice() + " руб.").callbackData(String.valueOf(cultivar.getId()));
            inlineKeyboardMarkup.addRow(cultivarButton, minusButton, countButton, plusButton, priceButton);
        }

        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("« Назад").callbackData("bouquets"));
        return inlineKeyboardMarkup;
    }

}
