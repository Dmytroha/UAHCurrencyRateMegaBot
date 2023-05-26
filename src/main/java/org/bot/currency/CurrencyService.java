package org.bot.currency;

import org.bot.currency.dto.Currency;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;

public interface CurrencyService {

    double getRate(Currency currency) throws URISyntaxException, FileNotFoundException;

}
