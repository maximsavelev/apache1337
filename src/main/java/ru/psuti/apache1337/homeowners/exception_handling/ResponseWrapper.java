package ru.psuti.apache1337.homeowners.exception_handling;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class ResponseWrapper {

    private String response;

    private String status;
}