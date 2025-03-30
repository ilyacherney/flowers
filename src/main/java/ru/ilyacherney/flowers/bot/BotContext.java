package ru.ilyacherney.flowers.bot;

import com.pengrad.telegrambot.model.Update;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
public class BotContext {
    private State state;

    private final MenuState menuState;
    private final FlowersState flowersState;

    private final List<StateTransitionCommand> stateTransitionCommands;

    public BotContext(MenuState menuState, FlowersState flowersState, List<StateTransitionCommand> stateTransitionCommands) {
        this.state = menuState;
        this.menuState = menuState;
        this.flowersState = flowersState;
        this.stateTransitionCommands = stateTransitionCommands;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void handleUpdate(Update update) {
        for (StateTransitionCommand command : stateTransitionCommands) {
            if (command.isApplicable(update)) {
                command.execute(this);
                return;
            }
        }

        // Если не нашли команду перехода, обрабатываем обновление в текущем состоянии
        state.handleUpdate(update);
    }
}
