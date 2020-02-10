package com.labuda.roundup.model;

import java.util.List;

public class SavingsGoalsV2 {
    private List<SavingsGoalV2> savingsGoalList;

    public SavingsGoalsV2() {
    }

    public List<SavingsGoalV2> getSavingsGoalList() {
        return savingsGoalList;
    }

    public void setSavingsGoalList(List<SavingsGoalV2> savingsGoalList) {
        this.savingsGoalList = savingsGoalList;
    }
}
