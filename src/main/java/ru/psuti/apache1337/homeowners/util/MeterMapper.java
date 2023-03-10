package ru.psuti.apache1337.homeowners.util;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.psuti.apache1337.homeowners.dto.MeterDto;
import ru.psuti.apache1337.homeowners.entity.Meter;

@Component
@RequiredArgsConstructor
public class MeterMapper {

    private final ModelMapper mapper;

    public Meter toEntity(MeterDto meterDto) {
        return mapper.map(meterDto, Meter.class);
    }

    public MeterDto toDto(Meter meter) {
        return mapper.map(meter, MeterDto.class);
    }
}
