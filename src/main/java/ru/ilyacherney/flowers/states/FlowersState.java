package ru.ilyacherney.flowers.states;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import org.springframework.stereotype.Component;
import ru.ilyacherney.flowers.cultivar.Cultivar;
import ru.ilyacherney.flowers.cultivar.CultivarService;
import ru.ilyacherney.flowers.flower.FlowerService;

import java.util.List;

@Component
public class FlowersState implements State {

    private final TelegramBot bot;
    private final StateRenderer renderer;
    private final CultivarService cultivarService;
    private final FlowerService flowerService;

    public FlowersState(TelegramBot bot, StateRenderer renderer, CultivarService cultivarService, FlowerService flowerService) {
        this.bot = bot;
        this.renderer = renderer;
        this.cultivarService = cultivarService;
        this.flowerService = flowerService;
    }

    @Override
    public String getDisplayName() {
        return "Цветы";
    }

    @Override
    public void handleUpdate(Update update) {
    }


    @Override
    public InlineKeyboardMarkup getInlineKeyboardMarkup() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<Cultivar> cultivars = cultivarService.getAllCultivars();
        for (Cultivar cultivar : cultivars) {
            InlineKeyboardButton cultivarButton = new InlineKeyboardButton(cultivar.getName()).callbackData(cultivar.getName());
            InlineKeyboardButton countButton = new InlineKeyboardButton(String.valueOf(flowerService.countByCultivarIdAndBouquetIdIsNull(cultivar.getId()))).callbackData(String.valueOf(cultivar.getId()));
            inlineKeyboardMarkup.addRow(cultivarButton, countButton);
        }


        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Меню").callbackData("start"));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Добавить цветы").callbackData("add_flowers"));
        return inlineKeyboardMarkup;
    }

    @Override
    public void render(long chatId, int editingMessageId) {
        renderer.render((State) this, chatId, editingMessageId);
    }
}
