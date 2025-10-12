package ar.com.pichidev.homestock.common.exception;


import java.io.Serializable;


public record FieldError(String field, String message, CodeError code) implements Serializable {

}