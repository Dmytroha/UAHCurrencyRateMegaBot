package org.bot.buttons;

import org.bot.model.UserSettings;
import org.bot.service.UserStorage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class GetInfoButton {
    public static final String GET_INFO_COMMAND = "Отримати інфо";
    private final UserStorage userStorage;

    public GetInfoButton(UserStorage userStorage) {

        this.userStorage = userStorage;
    }

    public void execute(SendMessage message) {
        try {
            String chatId = message.getChatId();
            UserSettings userSettings = userStorage.getUsers().stream().filter(user -> user.getId().equals(chatId)).findFirst().orElseGet(() -> createDefaultUserSettings(chatId));

            message.setText("Інформація для користувача " + chatId + ": Ви обрали банк " + userSettings.getBank() + ", валюти " + Arrays.toString(userSettings.getCurrencies()) + ", та час сповіщення " + userSettings.getNotificationTime());

        } catch (Exception e) {
            System.err.println("Error: info button");
            e.printStackTrace();
        }

    }

    private UserSettings createDefaultUserSettings(String chatId) {
        UserSettings defaultSettings = new UserSettings(chatId, "ПриватБанк", new String[]{"USD"}, 2, LocalTime.of(9, 0));
//        userStorage.saveUsers(List.of(defaultSettings));
        return defaultSettings;
    }

    private void attachButtons(SendMessage message, List<String> buttons) {
        InlineKeyboardMarkup keyboardMarkup = InlineKeyboardMarkup.builder().keyboard(Collections.singletonList(buttons.stream().map(buttonName -> InlineKeyboardButton.builder().text(buttonName).callbackData(buttonName).build()).collect(Collectors.toList()))).build();

        message.setReplyMarkup(keyboardMarkup);
    }
}


