package ar.com.pichidev.homestock.common.handler;

import ar.com.pichidev.homestock.common.exception.ValidationException;
import ar.com.pichidev.homestock.common.rest.response.ErrorResponse;
import ar.com.pichidev.homestock.common.rest.response.ValidationErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class CommonExceptionHandler {

    @ExceptionHandler({ValidationException.class})
    public ResponseEntity<ValidationErrorResponse> handlerBadRequestException(ValidationException e){
        log.error("Bad request exception: " , e);
        return ResponseEntity.badRequest().body(
                new ValidationErrorResponse(e.getCode(), e.getMessage(), e.getFieldErrors())
        );

    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorResponse> handlerGenericException(Exception e){
        log.error("Generic exception: " , e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ErrorResponse("UNEXPECTED_ERROR", "An unexpected error has occurred")
        );

    }
}
