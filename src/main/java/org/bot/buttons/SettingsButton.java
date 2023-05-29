package org.bot.buttons;

import org.bot.model.UserSettings;
import org.bot.service.UserStorage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SettingsButton {
    private final UserStorage userStorage;

    public SettingsButton(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public void execute(SendMessage message) {
        String text = "Налаштування";
        message.setText(text);

        InlineKeyboardMarkup buttons = CommandFactory.buttons(Arrays.asList(
                "settings.precision:Кількість знаків після коми",
                "settings.bank:Банк",
                "settings.currency:Валюта",
                "settings.notification_time:Час сповіщень"
        ));
        message.setReplyMarkup(buttons);
    }

    public void precision(SendMessage message) {
        String chatId = message.getChatId();
        UserSettings userSettings = getUserSettings(chatId);

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

    public void precisionHandler(String chatId, String precision) {
        UserSettings userSettings = getUserSettings(chatId);
        int precisionValue = Integer.parseInt(precision);
        userSettings.setDecimals(precisionValue);
        saveUserSettings(userSettings);
        precision(createSendMessage(chatId));
    }

    public void bank(SendMessage message) {
        message.setText("Оберіть банк");
        InlineKeyboardMarkup buttons = CommandFactory.dataButtons(Arrays.asList(
                "settings.bank.data:НБУ",
                "settings.bank.data:ПриватБанк",
                "settings.bank.data:Монобанк"
        ));
        message.setReplyMarkup(buttons);
    }

    public void bankHandler(String chatId, String bank) {
        UserSettings userSettings = getUserSettings(chatId);
        userSettings.setBank(bank);

        saveUserSettings(userSettings);
    }

    public void currency(SendMessage message) {
        String chatId = message.getChatId();
        UserSettings userSettings = getUserSettings(chatId);

        message.setText("Оберіть валюту");

        List<String> options = new ArrayList<>();
        Set<String> currencies = new HashSet<>(Arrays.asList("USD", "EUR"));  // Доступні валюти
        for (String currency : currencies) {
            String option = "settings.currency.data:" + currency;
            if (Arrays.asList(userSettings.getCurrencies()).contains(currency)) {
                option += " ✅";
            }
            options.add(option);
        }

        InlineKeyboardMarkup buttons = CommandFactory.dataButtons(options);
        message.setReplyMarkup(buttons);
    }

    public void currencyHandler(String chatId, String currency) {
        UserSettings userSettings = getUserSettings(chatId);
        Set<String> currencies = new HashSet<>(Arrays.asList(userSettings.getCurrencies()));

        if (currencies.contains(currency)) {
            currencies.remove(currency);
        } else {
            currencies.add(currency);
        }

        userSettings.setCurrencies(currencies.toArray(new String[0]));

        saveUserSettings(userSettings);
    }

    public void time(SendMessage message) {
        message.setText("Оберіть час сповіщень");
        InlineKeyboardMarkup buttons = CommandFactory.dataButtons(Arrays.asList(
                "settings.time.data:9",
                "settings.time.data:10",
                "settings.time.data:11",
                "settings.time.data:12",
                "settings.time.data:13",
                "settings.time.data:14",
                "settings.time.data:15",
                "settings.time.data:16",
                "settings.time.data:17",
                "settings.time.data:disable"
        ));
        message.setReplyMarkup(buttons);
    }

    public void timeHandler(String chatId, String time) {
        UserSettings userSettings = getUserSettings(chatId);

        if (time.equals("disable")) {
            userSettings.setNotify(false);
            userSettings.setNotificationTime(null);
        } else {
            userSettings.setNotify(true);
            int hour = Integer.parseInt(time);
            userSettings.setNotificationTime(LocalTime.of(hour, 0, 0));
        }

        saveUserSettings(userSettings);
    }

    private UserSettings getUserSettings(String chatId) {
        return userStorage.getUsers().stream()
                .filter(user -> user.getId().equals(chatId))
                .findFirst()
                .orElseGet(() -> createDefaultUserSettings(chatId));
    }

    private void saveUserSettings(UserSettings userSettings) {
        List<UserSettings> users = userStorage.getUsers();
        users = users.stream()
                .map(user -> user.getId().equals(userSettings.getId()) ? userSettings : user)
                .collect(Collectors.toList());
        userStorage.saveUsers(users);
    }

    private UserSettings createDefaultUserSettings(String chatId) {
        UserSettings defaultSettings = new UserSettings(chatId);
        List<UserSettings> users = userStorage.getUsers();
        users.add(defaultSettings);
        userStorage.saveUsers(users);
        return defaultSettings;
    }

    private SendMessage createSendMessage(String chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        return message;
    }
}