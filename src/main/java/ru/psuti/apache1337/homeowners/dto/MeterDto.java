package ru.psuti.apache1337.homeowners.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.psuti.apache1337.homeowners.entity.MeterReading;
import ru.psuti.apache1337.homeowners.entity.Service;
import ru.psuti.apache1337.homeowners.entity.User;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeterDto {

    private Long id;

    private String name;

    private User user;

    private List<MeterReading> meterReadings;

    private Service service;
}
