package com.labuda.roundup.model;

import java.util.List;

public class ErrorResponse {
    private List<ErrorDetail> errors;
    private boolean success;

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "errors=" + errors +
                ", success=" + success +
                '}';
    }

    public ErrorResponse() {
    }

    public List<ErrorDetail> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorDetail> errors) {
        this.errors = errors;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
