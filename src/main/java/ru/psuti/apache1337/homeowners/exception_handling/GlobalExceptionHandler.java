package ru.psuti.apache1337.homeowners.exception_handling;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ResponseWrapper> handleException(NotFoundException exception) {
        log.warn("An exception was thrown :", exception);
        return new ResponseEntity<>(
                new ResponseWrapper(exception.getMessage(),
                        HttpStatus.NOT_FOUND.toString()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ResponseWrapper> handleException(IncorrectMeterReadingException exception) {
        log.warn("An exception was thrown :", exception);
        return new ResponseEntity<>(
                new ResponseWrapper(exception.getMessage(),
                        HttpStatus.BAD_REQUEST.toString()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ResponseWrapper> handleException(RepeatedMeterNameException exception) {
        log.warn("An exception was thrown :", exception);
        return new ResponseEntity<>(
                new ResponseWrapper(exception.getMessage(),
                        HttpStatus.BAD_REQUEST.toString()), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler
    public ResponseEntity<ResponseWrapper> handleException(DataAccessResourceFailureException exception) {
        log.warn("An exception was thrown :", exception);
        return new ResponseEntity<>(
                new ResponseWrapper(exception.getMessage(),
                        HttpStatus.SERVICE_UNAVAILABLE.toString()), HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler
    public ResponseEntity<ResponseWrapper> handleException(Exception exception) {
        log.warn("An exception was thrown :", exception);
        return new ResponseEntity<>(new ResponseWrapper(exception.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.toString()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}