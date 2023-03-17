package ru.psuti.apache1337.homeowners.exception_handling;

public class RepeatedMeterNameException extends RuntimeException {

    public RepeatedMeterNameException(String message) {
        super(message);
    }
}