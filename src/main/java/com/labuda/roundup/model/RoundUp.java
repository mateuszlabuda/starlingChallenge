package com.labuda.roundup.model;

public class RoundUp {
    private String goalCategoryUid;
    private CurrencyAndAmount amount;

    @Override
    public String toString() {
        return "RoundUp{" +
                "goalCategoryUid='" + goalCategoryUid + '\'' +
                ", amount=" + amount +
                '}';
    }

    public String getGoalCategoryUid() {
        return goalCategoryUid;
    }

    public void setGoalCategoryUid(String goalCategoryUid) {
        this.goalCategoryUid = goalCategoryUid;
    }

    public CurrencyAndAmount getAmount() {
        return amount;
    }

    public void setAmount(CurrencyAndAmount amount) {
        this.amount = amount;
    }
}
