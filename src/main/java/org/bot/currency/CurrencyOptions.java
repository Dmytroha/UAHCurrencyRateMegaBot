package org.bot.currency;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.DecimalFormat;

import org.bot.currency.dto.Currency;
import org.bot.currency.monobank.CurrencyParser;

public class CurrencyOptions {
    public static void main(String[] args) throws URISyntaxException, IOException {
        System.out.println(display("Monobank", "USD", 3));
    }

    // Метод на вывод информации по валюте, где принимает бакн, валюту, знаки после
    // запятой
    public static String display(String bank, String currency, int decimal) throws URISyntaxException, IOException {

        // Переменная куда получим значение валюті банка
        double buy = 0.0;
        double sale = 0.0;
        // Проходимся по банкам
        switch (bank) {
            case "ПриватБанк":
                if(currency.equals("USD") || currency.equals("EUR")){
                    buy = new PrivatCurrencyService().getRate(Currency.valueOf(currency));
                    sale = new PrivatCurrencyService().getRate(Currency.valueOf(currency));
                }
                else{
                    buy = new NbuCurrencyService().getRate(Currency.valueOf(currency));
                    sale = new NbuCurrencyService().getRate(Currency.valueOf(currency));
                }
                break;
            case "НБУ":
                buy = new NbuCurrencyService().getRate(Currency.valueOf(currency));
                sale = new NbuCurrencyService().getRate(Currency.valueOf(currency));
                break;
            case "Монобанк":
                if (currency.equals("USD") || currency.equals("EUR")){
                    buy = new CurrencyParser().getCurrency(currency).getRateBuy();
                    sale = new CurrencyParser().getCurrency(currency).getRateSell();
                }
                else{
                    buy = new CurrencyParser().getCurrency(currency).getRateCross();
                    sale = new CurrencyParser().getCurrency(currency).getRateCross();
                }
                break;
        }
        // форматирование количества значений после запятой
        DecimalFormat df = new DecimalFormat("#." + "0".repeat(decimal));
        String buyVal = df.format(buy);
        String saleVal = df.format(sale);
        // Возврат информации ввиде строки
        return "===============\nКурс: " + currency + "/UAH\n===============\nПокупка: " + buyVal + "\nПродаж: " +saleVal;

    }
}
