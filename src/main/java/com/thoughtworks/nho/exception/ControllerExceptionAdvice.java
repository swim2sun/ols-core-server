package com.thoughtworks.nho.exception;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionAdvice {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({SQLException.class, InvalidCredentialException.class})
    public ExceptionResp handleSQLException(InvalidCredentialException exception) {
        return ExceptionResp.of(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({BadCredentialsException.class})
    public ExceptionResp handleBadCredentialsException(BadCredentialsException exception) {
        String message = exception.getMessage();
        log.error(message);
        return ExceptionResp.of(message);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({UserExistedException.class})
    public ExceptionResp handleUserExistedException(UserExistedException exception) {
        return ExceptionResp.of(exception.getLocalizedMessage());
    }

    @Data
    @AllArgsConstructor(staticName = "of")
    static class ExceptionResp {
        private String message;
    }
}
