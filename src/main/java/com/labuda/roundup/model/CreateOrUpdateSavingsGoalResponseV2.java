package com.labuda.roundup.model;

import java.util.List;

public class CreateOrUpdateSavingsGoalResponseV2 {
    private String savingsGoalUid;
    private boolean success;
    private List<ErrorDetail> errors;

    @Override
    public String toString() {
        return "CreateOrUpdateSavingsGoalResponseV2{" +
                "savingsGoalUid='" + savingsGoalUid + '\'' +
                ", success=" + success +
                ", errors=" + errors +
                '}';
    }

    public CreateOrUpdateSavingsGoalResponseV2() {
    }

    public String getSavingsGoalUid() {
        return savingsGoalUid;
    }

    public void setSavingsGoalUid(String savingsGoalUid) {
        this.savingsGoalUid = savingsGoalUid;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<ErrorDetail> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorDetail> errors) {
        this.errors = errors;
    }
}
