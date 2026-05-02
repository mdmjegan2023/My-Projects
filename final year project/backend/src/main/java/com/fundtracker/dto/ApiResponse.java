package com.fundtracker.dto;

public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;

    // No-arg constructor
    public ApiResponse() {}

    // All-args constructor
    public ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data    = data;
    }

    // ── Static factory methods ────────────────────────────────────────────────
    // Explicit <T> on each method lets the compiler infer the type argument
    // correctly even when data is null (fixes "cannot infer type arguments").

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data);
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, "Success", data);
    }

    /** Use this overload when there is no payload (e.g. delete endpoints). */
    public static ApiResponse<Void> successVoid(String message) {
        return new ApiResponse<>(true, message, null);
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(false, message, null);
    }

    // Getters
    public boolean isSuccess()  { return success; }
    public String  getMessage() { return message; }
    public T       getData()    { return data; }

    // Setters
    public void setSuccess(boolean success) { this.success = success; }
    public void setMessage(String message)  { this.message = message; }
    public void setData(T data)             { this.data    = data; }
}
