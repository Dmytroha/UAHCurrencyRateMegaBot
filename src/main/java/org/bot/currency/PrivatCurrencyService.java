package org.bot.currency;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.bot.currency.dto.Currency;
import org.bot.currency.dto.privat.CurrencyPrivatItem;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.List;

public class PrivatCurrencyService implements CurrencyService {
    @Override
    public double getRate(Currency currency) throws URISyntaxException, FileNotFoundException {
        /*
        String URL = "https://api.privatbank.ua/p24api/exchange_rates?date=" + getCurrentDate();

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

         */

        String path = "/Users/olha/Desktop/Projects/UAHCurrencyRateMegaBot/src/main/resources/model/privat.json";
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
                .getParameterized(List.class, CurrencyPrivatItem.class)
                .getType();
        List<CurrencyPrivatItem> currencyItems = new Gson().fromJson(String.valueOf(json), typeToken);

        return currencyItems.stream()
                .filter(it -> it.getCcy() == currency)
                .filter(it -> it.getBase_ccy() == Currency.UAH)
                .map(CurrencyPrivatItem::getBuy)
                .findFirst()
                .orElseThrow();
    }

}
