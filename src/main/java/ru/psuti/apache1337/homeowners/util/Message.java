package ru.psuti.apache1337.homeowners.util;

import lombok.Data;

@Data
public class Message {

    private String message;

    public Message(String message) {
        this.message = message;
    }

}
