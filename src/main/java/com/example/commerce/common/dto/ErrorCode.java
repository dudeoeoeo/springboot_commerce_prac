package com.example.commerce.common.dto;

public enum ErrorCode {

    // USUAL
    INVALID_INPUT_VALUE(400, "USUAL_001", " Invalid Input Value"),
    METHOD_NOT_ALLOWED(405, "USUAL_002", "Method not allowed"),
    HANDLE_ACCESS_DENIED(403, "USUAL_003", "Access is Denied"),

    // Standard
    ILLEGAL_STATE(400, "STANDARD_001", "illegal state"),
    ILLEGAL_ARGUMENT(400, "STANDARD_002", "illegal argument"),

    // Exception
    EXCEPTION(500, "EXCEPTION", "exception");

    private int status;
    private final String code;
    private final String message;

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    protected int getStatus() { return status; }
    protected String getCode() { return code; }
    protected String getMessage() { return message; }
}
