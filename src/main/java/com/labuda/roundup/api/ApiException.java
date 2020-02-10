package com.labuda.roundup.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClientException;

/**
 * Exception thrown from ApiService captures server respoonse status codes & errors
 */

public class ApiException extends WebClientException {
    private HttpStatus status;
    private String message;

    @Override
    public String getMessage() {
        return String.format("%s %s", status, message);
    }

    public ApiException(HttpStatus status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }
}
