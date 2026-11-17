package com.example.deliveryapp.global.exception;



import org.springframework.validation.BindingResult;

import java.util.List;

public record ErrorResponse(
        int status,
        String code,
        String message,
        List<FieldErrorDetail> errors
) {

    public record FieldErrorDetail(
            String field,
            String reason
    ) {
    }

    public static ErrorResponse of(ErrorCode errorCode) {
        return new ErrorResponse(
                errorCode.getStatus().value(),
                errorCode.name(),
                errorCode.getMessage(),
                List.of()
        );
    }

    public static ErrorResponse of(ErrorCode errorCode, BindingResult bindingResult) {
        List<FieldErrorDetail> fieldErrors = bindingResult.getFieldErrors().stream()
                .map(error -> new FieldErrorDetail(error.getField(), error.getDefaultMessage()))
                .toList();

        return new ErrorResponse(
                errorCode.getStatus().value(),
                errorCode.name(),
                errorCode.getMessage(),
                fieldErrors
        );
    }
}