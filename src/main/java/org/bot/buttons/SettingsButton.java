package org.bot.buttons;

import org.bot.model.UserSettings;
import org.bot.service.UserStorage;
import org.bot.telegram.CurrencyTelegramBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

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
        InlineKeyboardMarkup buttons = ButtonFactory.createPrecisionOptions(userSettings);
        message.setReplyMarkup(buttons);
    }

    public void precisionHandler(CurrencyTelegramBot currencyTelegramBot, Update update, String chatId, String precision) {
        UserSettings userSettings = getUserSettings(chatId);
        int precisionValue = Integer.parseInt(precision);
        userSettings.setDecimals(precisionValue);
        saveUserSettings(userSettings);
        precision(createSendMessage(chatId));
        changeButtonToSelected(update,userSettings,currencyTelegramBot,chatId);
    }

    private void changeButtonToSelected(Update update, UserSettings userSettings, CurrencyTelegramBot bot, String chatId) {
        EditMessageReplyMarkup editMessageReplyMarkup = new EditMessageReplyMarkup();
        editMessageReplyMarkup.setChatId(chatId);
        editMessageReplyMarkup.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
        InlineKeyboardMarkup buttons = ButtonFactory.createPrecisionOptions(userSettings);
        editMessageReplyMarkup.setReplyMarkup(buttons);
        try {
            bot.execute(editMessageReplyMarkup);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    private void changeCurrencyButtonToSelected(Update update, UserSettings userSettings, CurrencyTelegramBot bot, String chatId) {
        EditMessageReplyMarkup editMessageReplyMarkup = new EditMessageReplyMarkup();
        editMessageReplyMarkup.setChatId(chatId);
        editMessageReplyMarkup.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
        InlineKeyboardMarkup buttons = ButtonFactory.createCurrencyOptions(userSettings);
        editMessageReplyMarkup.setReplyMarkup(buttons);
        try {
            bot.execute(editMessageReplyMarkup);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    public void bank(SendMessage message) {
        String chatId = message.getChatId();
        UserSettings userSettings = getUserSettings(chatId);
        message.setText("Оберіть банк");
        InlineKeyboardMarkup buttons = ButtonFactory.createBankOptions(userSettings);
        message.setReplyMarkup(buttons);
    }

    public void bankHandler(CurrencyTelegramBot currencyTelegramBot, Update update, String chatId, String bank) {
        UserSettings userSettings = getUserSettings(chatId);
        userSettings.setBank(bank);
        saveUserSettings(userSettings);
        bank(createSendMessage(chatId));
        changeButtonToSelectedForBank(update,userSettings,currencyTelegramBot,chatId);
    }

    private void changeButtonToSelectedForBank(Update update, UserSettings userSettings, CurrencyTelegramBot bot, String chatId) {
        EditMessageReplyMarkup editMessageReplyMarkup = new EditMessageReplyMarkup();
        editMessageReplyMarkup.setChatId(chatId);
        editMessageReplyMarkup.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
        InlineKeyboardMarkup buttons = ButtonFactory.createBankOptions(userSettings);
        editMessageReplyMarkup.setReplyMarkup(buttons);
        try {
            bot.execute(editMessageReplyMarkup);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void currency(SendMessage message) {
        String chatId = message.getChatId();
        UserSettings userSettings = getUserSettings(chatId);
        message.setText("Оберіть валюту");
        InlineKeyboardMarkup buttons = ButtonFactory.createCurrencyOptions(userSettings);
        message.setReplyMarkup(buttons);
    }

    public void currencyHandler(CurrencyTelegramBot currencyTelegramBot, Update update, String chatId, String currency) {
        UserSettings userSettings = getUserSettings(chatId);
        Set<String> currencies = new HashSet<>(Arrays.asList(userSettings.getCurrencies()));

        currency = currency.replace(" ✅", "");
        if (currencies.contains(currency)) {
            currencies.remove(currency);
        } else {
            currencies.add(currency);
        }

        userSettings.setCurrencies(currencies.toArray(new String[0]));

        saveUserSettings(userSettings);
        changeCurrencyButtonToSelected(update,userSettings,currencyTelegramBot,chatId);
    }


    public void time(SendMessage message) {
        String chatId = message.getChatId();
        UserSettings userSettings = getUserSettings(chatId);

        message.setText("Оберіть час сповіщень");

        List<String> options = new ArrayList<>();
        for (int i = 9; i <= 17; i++) {
            String option = "settings.time.data:" + i;
            LocalTime userNotificationTime = userSettings.getNotificationTime();
            if (userSettings.isNotify() && userNotificationTime != null && userNotificationTime.getHour() == i) {
                option += " ✅";
            }
            options.add(option);
        }
        options.add("settings.time.data:disable" + (userSettings.isNotify() ? "" : " ✅"));

        InlineKeyboardMarkup buttons = CommandFactory.dataButtons(options);
        message.setReplyMarkup(buttons);
    }

    public void timeHandler(CurrencyTelegramBot currencyTelegramBot, Update update, String chatId, String time) {
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
        time(createSendMessage(chatId));
        changeButtonToSelectedForTime(update,userSettings,currencyTelegramBot,chatId);
    }

    private void changeButtonToSelectedForTime(Update update, UserSettings userSettings, CurrencyTelegramBot bot, String chatId) {
        EditMessageReplyMarkup editMessageReplyMarkup = new EditMessageReplyMarkup();
        editMessageReplyMarkup.setChatId(chatId);
        editMessageReplyMarkup.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
        List<String> options = new ArrayList<>();
        for (int i = 9; i <= 17; i++) {
            String option = "settings.time.data:" + i;
            LocalTime userNotificationTime = userSettings.getNotificationTime();
            if (userSettings.isNotify() && userNotificationTime != null && userNotificationTime.getHour() == i) {
                option += " ✅";
            }
            options.add(option);
        }
        options.add("settings.time.data:disable" + (userSettings.isNotify() ? "" : " ✅"));

        InlineKeyboardMarkup buttons = CommandFactory.dataButtons(options);
        editMessageReplyMarkup.setReplyMarkup(buttons);
        try {
            bot.execute(editMessageReplyMarkup);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
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