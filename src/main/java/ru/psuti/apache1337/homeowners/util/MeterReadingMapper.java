package ru.psuti.apache1337.homeowners.util;

import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.psuti.apache1337.homeowners.dto.MeterReadingDto;
import ru.psuti.apache1337.homeowners.dto.UserDto;
import ru.psuti.apache1337.homeowners.entity.MeterReading;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class MeterReadingMapper {

    private final ModelMapper mapper;

    public MeterReading toEntity(MeterReadingDto meterReadingDto) {
        return mapper.map(meterReadingDto, MeterReading.class);
    }

    public MeterReadingDto toDto(MeterReading meterReading) {
        return mapper.map(meterReading, MeterReadingDto.class);
    }

}
