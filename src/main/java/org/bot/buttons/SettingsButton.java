package org.bot.buttons;


import org.bot.model.UserSettings;
import org.bot.service.UserStorage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SettingsButton {
    private final UserStorage userStorage;

    public SettingsButton(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public void execute(SendMessage message) {
        String text = "Налаштування";
        message.setText(text);

        InlineKeyboardMarkup buttons = CommandFactory.buttons(Arrays.asList("settings.precision:Кількість знаків після коми", "settings.bank:Банк", "settings.currency:Валюти", "settings.notification_time:Час оповіщень"));
        message.setReplyMarkup(buttons);
    }

    public void precision(SendMessage message) {
        String chatId = message.getChatId();
        UserSettings userSettings = userStorage.getUsers().stream().filter(user -> user.getId().equals(chatId)).findFirst().orElseGet(() -> createDefaultUserSettings(chatId));

        message.setText("Оберіть кількість знаків після коми");

        List<String> options = new ArrayList<>();
        for (int i = 2; i <= 4; i++) {
            String option = "settings.precision.data:" + i;
            if (userSettings.getDecimals() == i) {
                option += " ✅";
            }
            options.add(option);
        }

        InlineKeyboardMarkup buttons = CommandFactory.dataButtons(options);
        message.setReplyMarkup(buttons);
    }

    public void precisionHandler(String precision) {

        System.out.println(precision);

    }



    public void bank(SendMessage message) {
        message.setText("Оберіть банк");
        InlineKeyboardMarkup buttons = CommandFactory.dataButtons(Arrays.asList("settings.precision.data:  НБУ ", "settings.precision.data: Приватбанк ", "settings.precision.data:  Монобанк "));
        message.setReplyMarkup(buttons);
    }

    public void bankHandler(String bank) {
        System.out.println(bank);
    }

    public void currency(SendMessage message) {
        message.setText("Оберіть валюту");
        InlineKeyboardMarkup buttons = CommandFactory.dataButtons(Arrays.asList("settings.precision.data: USD ", "settings.precision.data:  EUR "));
        message.setReplyMarkup(buttons);
    }

    public void currencyHandler(String currency) {
        System.out.println(currency);
    }

    public void time(SendMessage message) {
        message.setText("Оберіть час оповіщень");
        InlineKeyboardMarkup buttons = CommandFactory.dataButtons(Arrays.asList("settings.time.data:9", "settings.time.data:10", "settings.time.data:11 ", "settings.time.data:  12 ", "settings.time.data:  13 ", "settings.time.data:  14 ", "settings.time.data:  15 ", "settings.time.data:  16 ", "settings.time.data:  17 "));
        message.setReplyMarkup(buttons);
    }

    public void timeHandler(String time) {
        System.out.println(time);
    }
    private UserSettings createDefaultUserSettings(String chatId) {
        UserSettings defaultSettings = new UserSettings(chatId);
        List<UserSettings> users = userStorage.getUsers();
        users.add(defaultSettings);
        userStorage.saveUsers(users);
        return defaultSettings;
    }
}

