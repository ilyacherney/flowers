package ru.ilyacherney.flowers.flower;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import org.springframework.stereotype.Component;
import ru.ilyacherney.flowers.cultivar.Cultivar;
import ru.ilyacherney.flowers.cultivar.CultivarService;
import ru.ilyacherney.flowers.states.State;
import ru.ilyacherney.flowers.states.StateRenderer;

import java.util.List;

@Component
public class FlowersState implements State {

    private final TelegramBot bot;
    private final StateRenderer renderer;
    private final CultivarService cultivarService;
    private final FlowerService flowerService;

    private int editingMessageId;

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
        if (update.callbackQuery() == null) return;

        String data = update.callbackQuery().data();
        long chatId = update.callbackQuery().message().chat().id();

        if (data.startsWith("add_flower_of_cultivar_id:")) {
            long cultivarId = Long.parseLong(data.substring("add_flower_of_cultivar_id:".length()));
            Cultivar cultivar = cultivarService.getCultivarById(cultivarId);
            flowerService.createFlower(cultivar);
            renderer.render(this, chatId, editingMessageId, update.callbackQuery());
        }

        if (data.startsWith("remove_flower_of_cultivar_id:")) {
            long cultivarId = Long.parseLong(data.substring("remove_flower_of_cultivar_id:".length()));
            Cultivar cultivar = cultivarService.getCultivarById(cultivarId);
            flowerService.deleteByCultivarId(cultivar.getId());
            renderer.render(this, chatId, editingMessageId, update.callbackQuery());
        }
    }


    @Override
    public InlineKeyboardMarkup getInlineKeyboardMarkup() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<Cultivar> cultivars = cultivarService.getAllCultivars();
        for (Cultivar cultivar : cultivars) {
            InlineKeyboardButton cultivarButton = new InlineKeyboardButton(cultivar.getName()).callbackData(cultivar.getName());
            InlineKeyboardButton minusButton = new InlineKeyboardButton("-").callbackData("remove_flower_of_cultivar_id:" + cultivar.getId());
            InlineKeyboardButton countButton = new InlineKeyboardButton(String.valueOf(flowerService.countByCultivarIdAndBouquetIdIsNull(cultivar.getId())) + " шт.").callbackData(String.valueOf(cultivar.getId()));
            InlineKeyboardButton plusButton = new InlineKeyboardButton("+").callbackData("add_flower_of_cultivar_id:" + cultivar.getId());
            InlineKeyboardButton priceButton = new InlineKeyboardButton(cultivar.getPrice() + " руб.").callbackData(String.valueOf(cultivar.getId()));
            inlineKeyboardMarkup.addRow(cultivarButton, minusButton, countButton, plusButton, priceButton);
        }

        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("« Назад").callbackData("start"));
        return inlineKeyboardMarkup;
    }

    @Override
    public void render(long chatId, int editingMessageId) {
        this.editingMessageId = editingMessageId;
        renderer.render((State) this, chatId, editingMessageId);
    }
}