package com.labuda.roundup.model;

import java.util.List;

public class SavingsGoalTransferResponseV2 {

    private String transferUid;
    private boolean success;
    private List<ErrorDetail> errors;

    @Override
    public String toString() {
        return "SavingsGoalTransferResponseV2{" +
                "transferUid='" + transferUid + '\'' +
                ", success=" + success +
                ", errors=" + errors +
                '}';
    }

    public SavingsGoalTransferResponseV2() {
    }

    public String getTransferUid() {
        return transferUid;
    }

    public void setTransferUid(String transferUid) {
        this.transferUid = transferUid;
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
