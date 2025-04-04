package ru.ilyacherney.flowers.states;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import org.springframework.stereotype.Component;
import ru.ilyacherney.flowers.cultivar.Cultivar;
import ru.ilyacherney.flowers.cultivar.CultivarService;
import ru.ilyacherney.flowers.flower.FlowerService;

import java.util.List;

@Component
public class AddFlowersState implements State {

    private final StateRenderer renderer;
    private final CultivarService cultivarService;
    private final FlowerService flowerService;

    private int editingMessageId;

    public AddFlowersState(StateRenderer renderer, CultivarService cultivarService, FlowerService flowerService) {
        this.renderer = renderer;
        this.cultivarService = cultivarService;
        this.flowerService = flowerService;
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
            // Рендерим с callbackQuery для подтверждения
            renderer.render(this, chatId, editingMessageId, update.callbackQuery());
        }
    }

    @Override
    public void render(long chatId, int editingMessageId) {
        this.editingMessageId = editingMessageId;
        renderer.render(this, chatId, editingMessageId);
    }

    @Override
    public String getDisplayName() {
        return "Здесь вы можете добавить цветы в наличие";
    }

    @Override
    public InlineKeyboardMarkup getInlineKeyboardMarkup() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<Cultivar> cultivars = cultivarService.getAllCultivars();
        for (Cultivar cultivar : cultivars) {
            InlineKeyboardButton cultivarButton = new InlineKeyboardButton(cultivar.getName()).callbackData(cultivar.getName());
            InlineKeyboardButton plusButton = new InlineKeyboardButton("+").callbackData("add_flower_of_cultivar_id:" + cultivar.getId());
            inlineKeyboardMarkup.addRow(cultivarButton, plusButton);
        }

        return inlineKeyboardMarkup;
    }
}
