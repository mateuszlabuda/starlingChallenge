package com.labuda.roundup.model;

public class AccountV2 {

    private String accountUid;
    private String defaultCategory;
    private String currency;
    private String createdAt;

    @Override
    public String toString() {
        return "AccountV2{" +
                "accountUid='" + accountUid + '\'' +
                ", defaultCategory='" + defaultCategory + '\'' +
                ", currency='" + currency + '\'' +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }

    public String getAccountUid() {
        return accountUid;
    }

    public void setAccountUid(String accountUid) {
        this.accountUid = accountUid;
    }

    public String getDefaultCategory() {
        return defaultCategory;
    }

    public void setDefaultCategory(String defaultCategory) {
        this.defaultCategory = defaultCategory;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
