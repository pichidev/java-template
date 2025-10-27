package ar.com.pichidev.template.common.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class ValidationException extends RuntimeException {
    private final List<FieldError> fieldErrors;

    public ValidationException(List<FieldError> fieldErrors) {
        super("Input parameter error. See the error list for more details.");
        this.fieldErrors = fieldErrors;
    }

    public String getCode() {
        return "VALIDATION_ERROR";
    }

    @Override
    public String toString() {
        return super.toString() + " ValidationException{" + "fieldErrors=" + fieldErrors + '}';
    }
}
