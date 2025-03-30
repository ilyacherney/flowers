package ru.ilyacherney.flowers.states;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.EditMessageText;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Service;

@Service
public class StateRenderer {

    private final TelegramBot bot;

    public StateRenderer(TelegramBot bot) {
        this.bot = bot;
    }

    public void render(State state, long chatId, int editingMessageId) {
        SendMessage message = new SendMessage(chatId, state.getDisplayName());
        message.parseMode(ParseMode.HTML);
        InlineKeyboardMarkup keyboard = state.getInlineKeyboardMarkup();
        message.replyMarkup(keyboard);

        EditMessageText editMessageText = new EditMessageText(chatId, editingMessageId, state.getDisplayName())
                .replyMarkup(keyboard);

        bot.execute(editMessageText);
    }


    public void render(State state, long chatId) {
        SendMessage message = new SendMessage(chatId, state.getDisplayName());
        message.parseMode(ParseMode.HTML);
        InlineKeyboardMarkup keyboard = state.getInlineKeyboardMarkup();
        message.replyMarkup(keyboard);

        SendMessage sendMessage = new SendMessage(chatId, state.getDisplayName())
                .replyMarkup(keyboard);

        bot.execute(sendMessage);
    }
}
