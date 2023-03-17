package ru.psuti.apache1337.homeowners.exception_handling;

public class IncorrectMeterReadingException extends RuntimeException {

    public IncorrectMeterReadingException(String message) {
        super(message);
    }
}