package org.bot.telegram;


import org.bot.buttons.NotifyTimeButton;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.bot.buttons.StartButton;
import org.bot.buttons.GetInfoButton;
import org.bot.buttons.SettingsButton;
import org.bot.service.UserStorage;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.bot.buttons.GetInfoButton.GET_INFO_COMMAND;
import static org.bot.buttons.SettingsButton.SETTINGS_COMMAND;
import static org.bot.buttons.NotifyTimeButton.NOTIFY_TIME_COMMAND;
import static org.bot.buttons.NotifyTimeButton.TURN_OFF_NOTIFICATION;


public class CurrencyTelegramBot extends TelegramLongPollingCommandBot {
    private final GetInfoButton getInfoButton;
    private final SettingsButton settingsButton;
    private NotifyTimeButton notifyTimeButton;

    public CurrencyTelegramBot() {
        register(new StartButton());
        UserStorage userStorage = new UserStorage();
        getInfoButton = new GetInfoButton(userStorage);
        settingsButton = new SettingsButton();
        notifyTimeButton = new NotifyTimeButton(this);
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        /*Метод виконується, коли до бота приходить команда*/
        if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
            long chatId = update.getCallbackQuery().getMessage().getChatId();
            SendMessage message = new SendMessage();
            boolean process = processButtons(callbackData, chatId, message);
            if (process) {
                try {
                    super.execute(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private boolean processButtons(String messageText, long chatId, SendMessage message) {
        message.setChatId(String.valueOf(chatId));
        switch (messageText) {
            case GET_INFO_COMMAND:
                getInfoButton.execute(message);
                break;
            case SETTINGS_COMMAND:
                settingsButton.execute(message);
                break;
            case NOTIFY_TIME_COMMAND:
                notifyTimeButton.execute(message);
                break;
            case TURN_OFF_NOTIFICATION:
                //notifyTimeButton.handleNotificationTimeButton(update, callbackData);

            default:
                return false;
        }
        return true;
    }


    @Override
    public String getBotUsername() {
        return Constants.BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return Constants.BOT_TOKEN;
    }

}
