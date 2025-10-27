package ar.com.pichidev.template.common.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private final String code;

    public BusinessException(CodeError code, String message) {
        super(message);
        this.code = code.name();
    }
}
