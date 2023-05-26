package org.bot.currency.dto;

import lombok.Data;

@Data
public class CurrencyItem {

    private int r030;
    private String txt;
    private float rate;
    private Currency cc;
    private String exchangedate;

}
