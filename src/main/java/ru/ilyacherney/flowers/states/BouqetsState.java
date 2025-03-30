package ru.ilyacherney.flowers.states;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import org.springframework.stereotype.Component;


@Component
public class BouqetsState implements State {

    private final TelegramBot bot;
    private final StateRenderer renderer;

    public BouqetsState(TelegramBot bot, StateRenderer renderer) {
        this.bot = bot;
        this.renderer = renderer;
    }

    @Override
    public String getDisplayName() {
        return "Букеты";
    }

    @Override
    public void handleUpdate(Update update) {

    }

    @Override
    public InlineKeyboardMarkup getInlineKeyboardMarkup() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Меню").callbackData("start"));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Новый букет").callbackData("new_bouquet"));
        return inlineKeyboardMarkup;
    }

    @Override
    public void render(long chatId, int editingMessageId) {
        renderer.render((State) this, chatId, editingMessageId);
    }

}
