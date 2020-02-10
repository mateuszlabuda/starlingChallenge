package com.labuda.roundup.model;

public class TopUpRequestV2 {

    private CurrencyAndAmount amount;

    public TopUpRequestV2() {
    }

    public TopUpRequestV2(String currency, long amount) {
        this(new CurrencyAndAmount(currency, amount));
    }

    public TopUpRequestV2(CurrencyAndAmount amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "TopUpRequestV2{" +
                "amount=" + amount +
                '}';
    }

    public CurrencyAndAmount getAmount() {
        return amount;
    }

    public void setAmount(CurrencyAndAmount amount) {
        this.amount = amount;
    }
}
