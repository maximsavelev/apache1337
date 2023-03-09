package ru.psuti.apache1337.homeowners.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestDto {

    private Long id;

    private String title;

    private LocalDateTime date;
    @JsonProperty("address")
    private Long addressId;

    private String comment;


    private Integer status;
    @JsonProperty("client")
    private Long clientId;

    private String fileName;


}
