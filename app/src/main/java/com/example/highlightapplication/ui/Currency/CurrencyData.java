package com.example.highlightapplication.ui.Currency;

public class CurrencyData {
    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public CurrencyData(String currencyName) {
        this.currencyName = currencyName;
    }

    String currencyName;
}
