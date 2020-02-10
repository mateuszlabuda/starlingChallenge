package com.labuda.roundup.model;

public class SavingsGoalRequestV2 {

    private String name;
    private String currency;
    private CurrencyAndAmount target;
    private String base64EncodedPhoto;

    @Override
    public String toString() {
        return "SavingsGoalRequestV2{" +
                "name='" + name + '\'' +
                ", currency='" + currency + '\'' +
                ", target=" + target +
                ", base64EncodedPhoto='" + base64EncodedPhoto + '\'' +
                '}';
    }

    public SavingsGoalRequestV2() {
    }

    public SavingsGoalRequestV2(String name, String currency) {
        this.name = name;
        this.currency = currency;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public CurrencyAndAmount getTarget() {
        return target;
    }

    public void setTarget(CurrencyAndAmount target) {
        this.target = target;
    }

    public String getBase64EncodedPhoto() {
        return base64EncodedPhoto;
    }

    public void setBase64EncodedPhoto(String base64EncodedPhoto) {
        this.base64EncodedPhoto = base64EncodedPhoto;
    }
}
