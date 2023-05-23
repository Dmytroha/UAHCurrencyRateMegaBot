package org.bot.model;

import java.time.LocalTime;

public class UserSettings {
    private String id;
    private String bank;
    private String[] currencies;
    private int decimals;
    private LocalTime notificationTime;


    public UserSettings(String id, String bank, String[] currencies, int decimals, LocalTime notificationTime) {
        this.id = id;
        this.bank = bank;
        this.currencies = currencies;
        this.decimals = decimals;
        this.notificationTime = notificationTime;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String[] getCurrencies() {
        return currencies;
    }

    public void setCurrencies(String[] currencies) {
        this.currencies = currencies;
    }

    public int getDecimals() {
        return decimals;
    }

    public void setDecimals(int decimals) {
        this.decimals = decimals;
    }

    public LocalTime getNotificationTime() {
        return notificationTime;
    }

    public void setNotificationTime(LocalTime notificationTime) {
        this.notificationTime = notificationTime;
    }
}

