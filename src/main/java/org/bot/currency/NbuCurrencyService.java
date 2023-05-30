package org.bot.currency;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.bot.currency.dto.Currency;
import org.bot.currency.dto.CurrencyNbuItem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class NbuCurrencyService implements CurrencyService {

    @Override
    public double getRate(Currency currency) {
        /*
        String URL = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json";

        String json;
        try {
            json = Jsoup
                    .connect(URL)
                    .ignoreContentType(true)
                    .get()
                    .body()
                    .text();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Can't connect to NBU API");
        }

        Type typeToken = TypeToken
                .getParameterized(List.class, CurrencyItem.class)
                .getType();
        List<CurrencyItem> currencyItems = new Gson().fromJson(json, typeToken);

        return currencyItems.stream()
                .filter(it -> it.getCc() == currency)
                .map(CurrencyItem::getRate)
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

         */

        String path = "/Users/olha/Desktop/Projects/UAHCurrencyRateMegaBot/src/main/resources/model/nbu.json";
        StringBuilder json = new StringBuilder();
        String line;
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            while ((line = reader.readLine()) != null) {
                json.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Type typeToken = TypeToken
                .getParameterized(List.class, CurrencyNbuItem.class)
                .getType();
        List<CurrencyNbuItem> currencyItems = new Gson().fromJson(String.valueOf(json), typeToken);

        return currencyItems.stream()
                .filter(it -> it.getCc() == currency)
                .map(CurrencyNbuItem::getRate)
                .findFirst()
                .orElseThrow();
    }

}
