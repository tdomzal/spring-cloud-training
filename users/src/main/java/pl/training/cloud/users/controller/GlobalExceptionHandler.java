package pl.training.cloud.users.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import pl.training.cloud.users.dto.ExceptionDto;

import java.util.Locale;

@RequiredArgsConstructor
@ControllerAdvice
public class GlobalExceptionHandler {

    @NonNull
    private MessageSource messageSource;

    private ResponseEntity createResponse(Exception ex, HttpStatus status, Locale locale) {
        String exceptionName = ex.getClass().getSimpleName();
        String description;
        try {
            description = messageSource.getMessage(ex.getClass().getSimpleName(), null, locale);
        } catch (NoSuchMessageException exception) {
            description = exceptionName;

        }
        return ResponseEntity.status(status).body(new ExceptionDto(description));
    }

}
