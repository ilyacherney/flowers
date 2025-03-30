package ru.ilyacherney.flowers.states;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;

public interface State {
    void handleUpdate(Update update);
    void render(long chatId, int editingMessageId);
    String getDisplayName();
    InlineKeyboardMarkup getInlineKeyboardMarkup();
}
