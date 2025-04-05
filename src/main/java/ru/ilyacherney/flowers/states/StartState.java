package ru.ilyacherney.flowers.states;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import org.springframework.stereotype.Component;

@Component
public class StartState implements State {

    private final TelegramBot bot;

    private final StateRenderer renderer;

    public StartState(TelegramBot bot, StateRenderer renderer) {
        this.bot = bot;
        this.renderer = renderer;
    }

    @Override
    public String getDisplayName() {
        return "Меню";
    }

    @Override
    public void handleUpdate(Update update) {
        if (update.message() == null) {
            return;
        }
        if (update.message().text().equals("/start")) {
            renderer.render(this, update.message().chat().id());
        }
    }

    @Override
    public InlineKeyboardMarkup getInlineKeyboardMarkup() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Цветы").callbackData("flowers"));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Букеты").callbackData("bouquets"));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Продажи").callbackData("sales"));
        return inlineKeyboardMarkup;
    }

    @Override
    public void render(long chatId, int editingMessageId) {
        renderer.render((State) this, chatId, editingMessageId);
    }
}
