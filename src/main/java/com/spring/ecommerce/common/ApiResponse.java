package com.spring.ecommerce.common;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ApiResponse<T> {
    private final boolean success;
    private final String message;
    private T data;

    public ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

//    public boolean isSuccess() {
//        return success;
//    }
//
//    public String getMessage() {
//        return message;
//    }

    public String getTimestamp() {
        return LocalDateTime.now().toString();
    }
}
