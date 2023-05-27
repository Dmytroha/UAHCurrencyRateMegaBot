package org.bot.currency;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.bot.currency.dto.Currency;
import org.bot.currency.dto.CurrencyItem;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class NbuCurrencyService implements CurrencyService {

    @Override
    public double getRate(Currency currency) {
        //Please uncomment URL when button logic will be add (upon clicking it an information is requested)
        //String URL = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json";
        String URL = "template";

        //Get JSON
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

}
