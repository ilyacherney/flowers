package ru.ilyacherney.flowers.states;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.Keyboard;
import org.springframework.stereotype.Component;
import ru.ilyacherney.flowers.bouquet.Bouquet;
import ru.ilyacherney.flowers.bouquet.BouquetService;
import ru.ilyacherney.flowers.cultivar.Cultivar;
import ru.ilyacherney.flowers.cultivar.CultivarService;
import ru.ilyacherney.flowers.flower.Flower;
import ru.ilyacherney.flowers.flower.FlowerService;

import java.util.List;
import java.util.Set;

@Component
public class NewBouquetState implements State{

    private final StateRenderer stateRenderer;
    private final CultivarService cultivarService;
    private final FlowerService flowerService;
    private final BouquetService bouquetService;

    public NewBouquetState(StateRenderer stateRenderer, CultivarService cultivarService, FlowerService flowerService, BouquetService bouquetService) {
        this.stateRenderer = stateRenderer;
        this.cultivarService = cultivarService;
        this.flowerService = flowerService;
        this.bouquetService = bouquetService;
    }

    @Override
    public void handleUpdate(Update update) {
        String data = update.callbackQuery().data();
        long chatId = update.callbackQuery().message().chat().id();

        if (data.startsWith("add_flower_of_cultivar:")) {
            long cultivarId = Long.parseLong(data.substring("add_flower_of_cultivar:".length()));

            // Получаем или создаём активный букет
            Bouquet activeBouquet = bouquetService.getActiveBouquet();
            if (activeBouquet == null) {
                activeBouquet = bouquetService.createBouquet();  // Если букета нет, создаём новый
            }

            addFlowerOfCultivar(activeBouquet, cultivarId);
        }
    }


    private void addFlowerOfCultivar(Bouquet activeBouquet, long cultivarId) {
        Cultivar cultivar = cultivarService.getCultivarById(cultivarId);
        Set<Flower> cultivarFlowers = cultivar.getFlowers();

        for (Flower flower : cultivarFlowers) {
            if (flower.getBouquet() == null) {
                flower.setBouquet(activeBouquet);
                bouquetService.addFlowersToBouquet(List.of(flower));
            }
        }


    }



    @Override
    public void render(long chatId, int editingMessageId) {
        stateRenderer.render(this, chatId, editingMessageId);
    }

    @Override
    public String getDisplayName() {
        return "Выберете цветы:";
    }


    @Override
    public InlineKeyboardMarkup getInlineKeyboardMarkup() {

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<Cultivar> cultivars = cultivarService.getAllCultivars();
        for (Cultivar cultivar : cultivars) {
            InlineKeyboardButton minusButton = new InlineKeyboardButton("-").callbackData("- " + cultivar.getName());
            InlineKeyboardButton cultivarButton = new InlineKeyboardButton(cultivar.getName()).callbackData(cultivar.getName());
            InlineKeyboardButton plusButton = new InlineKeyboardButton("+").callbackData("add_flower_of_cultivar:" + cultivar.getId());
            inlineKeyboardMarkup.addRow(minusButton, cultivarButton, plusButton);
        }

        return inlineKeyboardMarkup;
    }

}
