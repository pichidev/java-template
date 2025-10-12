package ar.com.pichidev.homestock.common.exception;


import java.io.Serializable;

public class FieldError implements Serializable {
    private final String field;
    private final String message;
    private final CodeError code;

    public FieldError(String field, String message, CodeError code) {
        this.field = field;
        this.message = message;
        this.code = code;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }

    public CodeError getCode() {
        return code;
    }
}