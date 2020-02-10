package com.labuda.roundup.model;

public class CurrencyAndAmount {
    private String currency;
    private long minorUnits;

    @Override
    public String toString() {
        return "CurrencyAndAmount{" +
                "currency='" + currency + '\'' +
                ", minorUnits=" + minorUnits +
                '}';
    }

    public CurrencyAndAmount() {
    }

    public CurrencyAndAmount(String currency, long minorUnits) {
        this.currency = currency;
        this.minorUnits = minorUnits;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public long getMinorUnits() {
        return minorUnits;
    }

    public void setMinorUnits(int minorUnits) {
        this.minorUnits = minorUnits;
    }
}
