package ru.psuti.apache1337.homeowners.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeterReadingDto {
    private Long id;
    @JsonProperty("counter")
    private MeterDto meter;

    private LocalDateTime dateTime;

    private Integer value;
}
