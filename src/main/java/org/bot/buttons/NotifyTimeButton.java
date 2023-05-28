package org.bot.buttons;

import org.bot.telegram.CurrencyTelegramBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.bot.model.UserSettings;
import org.bot.service.UserStorage;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class NotifyTimeButton {
    private final CurrencyTelegramBot bot;
    public static final String NOTIFY_TIME_COMMAND = "Час оповіщень";
    public static final String TURN_OFF_NOTIFICATION = "Вимкнути повідомлення";


    public NotifyTimeButton(CurrencyTelegramBot bot) {
        this.bot = bot;
    }
    //додає години до клавіатури

    public void execute(SendMessage message) {
        String text = "Виберіть час оповіщень";
        //message = createMessage("Оберіть час оповіщень", chatId);
        List<String> buttons = new ArrayList<>();
        buttons = IntStream.rangeClosed(9, 18)
                .mapToObj(Integer::toString)
                .collect(Collectors.toList());
        buttons.add("Вимкнути повідомлення");
        //attachButtons(message, buttons);
        /*
        buttons.add("9:00");
        buttons.add("10:00");
        buttons.add("11:00");
        buttons.add("12:00");
        buttons.add("13:00");
        buttons.add("14:00");
        buttons.add("15:00");
        buttons.add("16:00");
        buttons.add("17:00");
        buttons.add("18:00");
        buttons.add("Вимкнути повідомлення");
         */
        attachButtonsKeyboard(message, buttons);
        message.setText(text);
        /*
        try {
            bot.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

         */
    }

    //якщо натиснуто на "Вимкнути повідомлення", вимикає повідомлення
    //якщо натиснуто на годину, то встановлює час сповіщень на цю годину

    public void handleNotificationTimeButton(Update update, String buttonText) {
        if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
            long chatId = update.getCallbackQuery().getMessage().getChatId();

            if (callbackData.equals("Вимкнути повідомлення")) {
                UserStorage userStorage = new UserStorage();
                List<UserSettings> userSettingsList = userStorage.getUsers();

                for (UserSettings userSettings : userSettingsList) {
                    if (userSettings.getId().equals(String.valueOf(chatId))) {
                        userSettings.setNotificationTime(null);  // Очистити час оповіщень
                        break;
                    }
                }

                userStorage.saveUsers(userSettingsList);

                SendMessage message = new SendMessage();
                message.setChatId(String.valueOf(chatId));
                message.setText("Сповіщення вимкнено");

                try {
                    bot.execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else {

                LocalTime selectedTime = LocalTime.parse(callbackData);


                UserStorage userStorage = new UserStorage();
                List<UserSettings> userSettingsList = userStorage.getUsers();

                for (UserSettings userSettings : userSettingsList) {
                    if (userSettings.getId().equals(String.valueOf(chatId))) {
                        userSettings.setNotificationTime(selectedTime);
                        break;
                    }
                }

                userStorage.saveUsers(userSettingsList);

                SendMessage message = new SendMessage();
                message.setChatId(String.valueOf(chatId));
                message.setText("Час оповіщень встановлено на " + selectedTime);

                try {
                    bot.execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    //створює клавіатуру

    private void attachButtonsKeyboard(SendMessage message, List<String> buttons) {
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();
        for (String buttonName : buttons) {
            row.add(new KeyboardButton(buttonName));
        }

        keyboard.add(row);

        ReplyKeyboardMarkup keyboardMarkup = ReplyKeyboardMarkup.builder()
                .keyboard(keyboard)
                .resizeKeyboard(true)
                .build();

        message.setReplyMarkup(keyboardMarkup);
    }

    private void attachButtons(SendMessage message, List<String> buttons) {
        InlineKeyboardMarkup keyboardMarkup = InlineKeyboardMarkup.builder()
                .keyboard(Collections.singletonList(
                        buttons.stream()
                                .map(buttonName -> InlineKeyboardButton.builder()
                                        .text(buttonName)
                                        .callbackData(buttonName)
                                        .build())
                                .collect(Collectors.toList())
                ))
                .build();

        message.setReplyMarkup(keyboardMarkup);
    }

}
