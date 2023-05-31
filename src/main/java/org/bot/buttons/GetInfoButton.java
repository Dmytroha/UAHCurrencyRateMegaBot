package org.bot.buttons;

import org.bot.currency.CurrencyOptions;
import org.bot.model.UserSettings;
import org.bot.service.UserStorage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.Arrays;
import java.util.List;


public class GetInfoButton {
    private final UserStorage userStorage;

    public GetInfoButton(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public void execute(SendMessage message) {
        try {
            String chatId = message.getChatId();
            if(userStorage.isNewPerson(chatId)){
                userStorage.writeNewUser(chatId);
            }
            UserSettings userSettings = userStorage.getUser(chatId);

            for (String currency : userSettings.getCurrencies()) {
                String exchangeRateInfo = CurrencyOptions.display(userSettings.getBank(), currency, userSettings.getDecimals());
                message.setText("Ви обрали банк " + userSettings.getBank() + "\nВалюта " + Arrays.toString(userSettings.getCurrencies()) + "\nЧас сповіщення " + userSettings.getNotificationTime() + ".\n" + exchangeRateInfo);
            }

        } catch (Exception e) {
            System.err.println("Error: info button");
            e.printStackTrace();
        }

        InlineKeyboardMarkup buttons = CommandFactory.buttons(Arrays.asList("info:Отримати інфо", "settings:Налаштування"));

        message.setReplyMarkup(buttons);
        try {
        } catch (Exception e) {
            System.out.println("can't send message to user");
        }
    }

    private UserSettings createDefaultUserSettings(String chatId) {
        return new UserSettings(chatId);
    }
}





