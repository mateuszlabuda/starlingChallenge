package com.labuda.roundup.model;

public class ErrorDetail {

    private String message;

    public ErrorDetail() {
    }

    public String getMessage() {
        //not sure why
        return message.replace("\r", "");
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
