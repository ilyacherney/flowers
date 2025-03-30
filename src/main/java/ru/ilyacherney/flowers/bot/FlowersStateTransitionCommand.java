package ru.ilyacherney.flowers.bot;


import com.pengrad.telegrambot.model.Update;
import org.springframework.stereotype.Component;

@Component
public class FlowersStateTransitionCommand implements StateTransitionCommand {
    @Override
    public boolean isApplicable(Update update) {
        return update.message() != null && "/flowers".equals(update.message().text());
    }

    @Override
    public void execute(BotContext botContext) {
        botContext.setState(botContext.getFlowersState());  // Переход в FlowersState
    }
}