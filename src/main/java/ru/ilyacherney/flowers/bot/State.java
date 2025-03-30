package ru.ilyacherney.flowers.bot;

import com.pengrad.telegrambot.model.Update;

public interface State {
    void handleUpdate(Update update);
}
