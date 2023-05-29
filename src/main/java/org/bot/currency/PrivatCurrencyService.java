package org.bot.currency;

import com.google.gson.Gson;
import org.bot.currency.dto.privat.CurrencyPrivatResponse;
import org.bot.currency.dto.Currency;
import org.jsoup.Jsoup;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PrivatCurrencyService implements CurrencyService {
    @Override
    public double getRate(Currency currency) throws URISyntaxException, FileNotFoundException {
        // Please uncomment URL when button logic will be add (upon clicking it an
        // information is requested)
        // String URL =
        // "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json";
        String URL = "https://api.privatbank.ua/p24api/exchange_rates?date=" + getCurrentDate();

        // Get JSON
        String json;
        try {
            json = Jsoup.connect(URL).ignoreContentType(true).get().body().text();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Can't connect to Privat API");
        }
        Gson gson = new Gson();
        CurrencyPrivatResponse currencyPrivatResponse = gson.fromJson(json, CurrencyPrivatResponse.class);
        return currencyPrivatResponse
                .getExchangeRate()
                .stream()
                .filter(it -> it.getCurrency().equals(currency.toString()))
                .findFirst().orElseThrow()
                .getSaleRate();
    }

    private String getCurrentDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

}
