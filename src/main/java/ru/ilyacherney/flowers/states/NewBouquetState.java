package ru.ilyacherney.flowers.states;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.Keyboard;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Component;
import ru.ilyacherney.flowers.bouquet.Bouquet;
import ru.ilyacherney.flowers.bouquet.BouquetService;
import ru.ilyacherney.flowers.cultivar.Cultivar;
import ru.ilyacherney.flowers.cultivar.CultivarService;
import ru.ilyacherney.flowers.flower.Flower;
import ru.ilyacherney.flowers.flower.FlowerService;

import java.util.List;
import java.util.Set;

@Transactional
@Component
public class NewBouquetState implements State{

    private final StateRenderer stateRenderer;
    private final CultivarService cultivarService;
    private final FlowerService flowerService;
    private final BouquetService bouquetService;

    public NewBouquetState(StateRenderer stateRenderer, CultivarService cultivarService, FlowerService flowerService, BouquetService bouquetService) {
        this.stateRenderer = stateRenderer;
        this.cultivarService = cultivarService;
        this.flowerService = flowerService;
        this.bouquetService = bouquetService;
    }

    @Override
    public void handleUpdate(Update update) {
        String data = update.callbackQuery().data();

        if (data.startsWith("add_flower_of_cultivar:")) {
            long cultivarId = Long.parseLong(data.substring("add_flower_of_cultivar:".length()));
            bouquetService.addFlowerToBouquet(cultivarId);
        }

        if (data.startsWith("remove_flower_of_cultivar:")) {
            long cultivarId = Long.parseLong(data.substring("remove_flower_of_cultivar:".length()));
            bouquetService.removeFlowerFromBouquet(cultivarId);
        }
    }

    @Override
    public void render(long chatId, int editingMessageId) {
        stateRenderer.render(this, chatId, editingMessageId);
    }

    @Override
    public String getDisplayName() {
//        if (bouquetService.getActiveBouquet() != null) {
//            return bouquetService.getActiveBouquet().toString();
//        }
        return "Выберете цветы:";
    }


    @Override
    public InlineKeyboardMarkup getInlineKeyboardMarkup() {

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<Cultivar> cultivars = cultivarService.getAllCultivars();
        for (Cultivar cultivar : cultivars) {
            InlineKeyboardButton minusButton = new InlineKeyboardButton("-").callbackData("remove_flower_of_cultivar:" + cultivar.getId());
            InlineKeyboardButton cultivarButton = new InlineKeyboardButton(cultivar.getName()).callbackData(cultivar.getName());
            InlineKeyboardButton plusButton = new InlineKeyboardButton("+").callbackData("add_flower_of_cultivar:" + cultivar.getId());
            inlineKeyboardMarkup.addRow(minusButton, cultivarButton, plusButton);
        }

        return inlineKeyboardMarkup;
    }

}
