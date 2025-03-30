package ru.ilyacherney.flowers.bot;

import com.pengrad.telegrambot.model.Update;

public interface StateTransitionCommand {
    boolean isApplicable(Update update);  // Проверка, применима ли команда
    void execute(BotContext botContext);  // Выполнение перехода
}