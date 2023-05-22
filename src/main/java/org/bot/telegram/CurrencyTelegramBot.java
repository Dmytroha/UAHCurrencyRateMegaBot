package org.bot.telegram;

import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.objects.Update;
/*
Это класс нашего бота
 */
public class CurrencyTelegramBot extends TelegramLongPollingCommandBot {


    @Override
    public void processNonCommandUpdate(Update update) {
        /*Метод выпоняется, когда в бот приходит комманда*/

        System.out.println("User made some action\\0/");

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
