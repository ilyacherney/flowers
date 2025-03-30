package ru.ilyacherney.flowers.bot;

import com.pengrad.telegrambot.model.Update;
import org.springframework.stereotype.Component;

@Component
public class MenuStateTransitionCommand implements StateTransitionCommand {
    @Override
    public boolean isApplicable(Update update) {
        return update.message() != null && "/menu".equals(update.message().text());
    }

    @Override
    public void execute(BotContext botContext) {
        botContext.setState(botContext.getMenuState());  // Переход в MenuState
    }
}