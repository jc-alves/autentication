package com.dev.autenticacao.infrastructure.util;

public class ApiResponse<T> {

    private final boolean success;
    private final T data;
    private final String errorMessage;

    public ApiResponse(T data, String errorMessage, boolean success) {
        this.success = success;
        this.data = data;
        this.errorMessage = errorMessage;
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(data, null, true);
    }

    public static <T> ApiResponse<T> failure(String errorMessage) {
        return new ApiResponse<>(null, errorMessage, false);
    }

    public boolean isSuccess() {
        return success;
    }

    public T getData() {
        return data;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
