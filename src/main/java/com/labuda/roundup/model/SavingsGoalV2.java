package com.labuda.roundup.model;

public class SavingsGoalV2 {

    private String savingsGoalUid;
    private String name;
    private CurrencyAndAmount target;
    private CurrencyAndAmount totalSaved;
    private int savedPercentage;

    @Override
    public String toString() {
        return "SavingsGoalV2{" +
                "savingsGoalUid='" + savingsGoalUid + '\'' +
                ", name='" + name + '\'' +
                ", target=" + target +
                ", totalSaved=" + totalSaved +
                ", savedPercentage=" + savedPercentage +
                '}';
    }

    public SavingsGoalV2() {
    }

    public String getSavingsGoalUid() {
        return savingsGoalUid;
    }

    public void setSavingsGoalUid(String savingsGoalUid) {
        this.savingsGoalUid = savingsGoalUid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CurrencyAndAmount getTarget() {
        return target;
    }

    public void setTarget(CurrencyAndAmount target) {
        this.target = target;
    }

    public CurrencyAndAmount getTotalSaved() {
        return totalSaved;
    }

    public void setTotalSaved(CurrencyAndAmount totalSaved) {
        this.totalSaved = totalSaved;
    }

    public int getSavedPercentage() {
        return savedPercentage;
    }

    public void setSavedPercentage(int savedPercentage) {
        this.savedPercentage = savedPercentage;
    }
}
