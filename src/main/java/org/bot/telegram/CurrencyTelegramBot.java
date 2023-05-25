package org.bot.telegram;

import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.bot.buttons.StartButton;
import org.bot.buttons.GetInfoButton;
import org.bot.buttons.SettingsButton;



/*
Это класс нашего бота
 */
public class CurrencyTelegramBot extends TelegramLongPollingCommandBot {
    private static final String START_COMMAND = "start";
    private static final String GET_INFO_COMMAND = "Отримати інфо";
    private static final String SETTINGS_COMMAND = "Налаштування";


    private GetInfoButton getInfoButton;
    private SettingsButton settingsButton;
    private StartButton startButton;

    public CurrencyTelegramBot() {
        startButton = new StartButton(this);
        getInfoButton = new GetInfoButton(this);
        settingsButton = new SettingsButton(this);
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        /*Метод виконується, коли до бота приходить команда*/
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            SendMessage message = new SendMessage();
            message.setChatId(String.valueOf(chatId));
            switch (messageText) {
                case "/" + START_COMMAND:
                    startButton.execute(message);
                    break;
                case GET_INFO_COMMAND:
                    getInfoButton.execute(message);
                    break;
                case SETTINGS_COMMAND:
                    settingsButton.execute(message);
                    break;
                default:
                    startButton.execute(message);
                    break;
            }
           

        } else if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
            long chatId = update.getCallbackQuery().getMessage().getChatId();
            SendMessage message = new SendMessage();
            message.setChatId(String.valueOf(chatId));
            switch (callbackData) {
                case GET_INFO_COMMAND:
                    getInfoButton.execute(message);
                    break;
                case SETTINGS_COMMAND:
                    settingsButton.execute(message);
                    break;
            }
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
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
