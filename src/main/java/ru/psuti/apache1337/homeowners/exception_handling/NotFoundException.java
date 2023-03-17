package ru.psuti.apache1337.homeowners.exception_handling;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }
}