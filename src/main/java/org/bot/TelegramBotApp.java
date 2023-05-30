package org.bot;

import lombok.SneakyThrows;
import org.bot.currency.CurrencyPrettier;
import org.bot.currency.CurrencyService;
import org.bot.currency.NbuCurrencyService;
import org.bot.currency.PrivatCurrencyService;
import org.bot.currency.dto.Currency;
import org.bot.currency.monobank.CurrencyParser;
import org.bot.telegram.TelegramBotService;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;

public class TelegramBotApp {
    @SneakyThrows
    public static void main(String[] args) throws FileNotFoundException, URISyntaxException {

        new TelegramBotService();


        /*
        CurrencyPrettier currencyPrettier = new CurrencyPrettier();

        //NBU
        System.out.println("NBU");
        CurrencyService nbuCurrencyService = new NbuCurrencyService();
        double rateNbuUSD = nbuCurrencyService.getRate(Currency.USD);
        double rateNbuEUR = nbuCurrencyService.getRate(Currency.EUR);
        double rateNbuPLN = nbuCurrencyService.getRate(Currency.PLN);


        System.out.println(currencyPrettier.convert(rateNbuUSD, Currency.USD, "NBU"));
        System.out.println(currencyPrettier.convert(rateNbuEUR, Currency.EUR, "NBU"));
        System.out.println(currencyPrettier.convert(rateNbuPLN, Currency.PLN, "NBU"));



        //PRIVAT
        System.out.println("\n\nPRIVAT");
        CurrencyService privatCurrencyService = new PrivatCurrencyService();
        double ratePrivatUSD = privatCurrencyService.getRate(Currency.USD);
        double ratePrivatEUR = privatCurrencyService.getRate(Currency.EUR);
        double ratePrivatPLN = privatCurrencyService.getRate(Currency.PLN);

        System.out.println(currencyPrettier.convert(ratePrivatUSD, Currency.USD, "PRIVAT"));
        System.out.println(currencyPrettier.convert(ratePrivatEUR, Currency.EUR, "PRIVAT"));
        System.out.println(currencyPrettier.convert(ratePrivatPLN, Currency.PLN, "PRIVAT"));



        //MONO
        System.out.println("\n\nMONO");
        CurrencyParser monoCurrencyService = new CurrencyParser();
        double rateMonoUSD = monoCurrencyService.getCurrency(Currency.USD.toString()).getRateBuy();
        double rateMonoEUR = monoCurrencyService.getCurrency(Currency.EUR.toString()).getRateBuy();
        double rateMonoPLN = monoCurrencyService.getCurrency(Currency.PLN.toString()).getRateBuy();
        System.out.println(currencyPrettier.convert(rateMonoUSD, Currency.USD, "MONO"));
        System.out.println(currencyPrettier.convert(rateMonoEUR, Currency.EUR, "MONO"));
        System.out.println(currencyPrettier.convert(rateMonoPLN, Currency.PLN, "MONO"));

         */
    }
}
