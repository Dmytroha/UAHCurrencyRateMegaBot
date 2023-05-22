package org.bot.telegram;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
/*
* Класс создает экземляр TelegramBotsApi и регистрирует нашего бота currencyTelegramBot
* */
public class TelegramBotService {
    private CurrencyTelegramBot currencyTelegramBot;

    public TelegramBotService() {
        this.currencyTelegramBot = new CurrencyTelegramBot();

        try { /*запуск бота*/
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(currencyTelegramBot);

        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

    }
}
