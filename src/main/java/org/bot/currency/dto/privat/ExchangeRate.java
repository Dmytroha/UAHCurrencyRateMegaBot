package org.bot.currency.dto.privat;

import lombok.Data;

@Data
public class ExchangeRate {


    private String baseCurrency;
    private String currency;
    private double saleRate;
    private double purchaseRate;


}