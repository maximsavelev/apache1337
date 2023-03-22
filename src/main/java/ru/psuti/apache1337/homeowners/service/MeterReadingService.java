package ru.psuti.apache1337.homeowners.service;

import ru.psuti.apache1337.homeowners.dto.MeterDto;
import ru.psuti.apache1337.homeowners.dto.MeterReadingDto;
import ru.psuti.apache1337.homeowners.entity.Meter;
import ru.psuti.apache1337.homeowners.entity.MeterReading;
import ru.psuti.apache1337.homeowners.repository.meter_readings.MeterReadingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.psuti.apache1337.homeowners.util.MeterMapper;
import ru.psuti.apache1337.homeowners.util.MeterReadingMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MeterReadingService {

    private final MeterReadingRepository meterReadingRepository;

    private final MeterReadingMapper meterReadingMapper;

    private final MeterMapper meterMapper;

    public MeterReadingDto findLatestMetersReadingByMeter(MeterDto meterDto) {
        return meterReadingMapper.toDto(meterReadingRepository.findLatestMeterReadingByMeter(meterMapper.toEntity(meterDto)));
    }

    public MeterReadingDto save(MeterReadingDto meterReadingDto) {
        return  meterReadingMapper
                .toDto(meterReadingRepository.save(meterReadingMapper.toEntity(meterReadingDto)));
    }

    public List<MeterReadingDto> findLatestMeterReadingByUserMeters(List<MeterDto> meterDto) {
        List<MeterReadingDto> values = new ArrayList<>();
        MeterReadingDto value;
        for (MeterDto meter : meterDto) {
            value = meterReadingMapper.toDto(meterReadingRepository.findLatestMeterReadingByMeter(meterMapper.toEntity(meter)));
            values.add(value);
        }
        return values;
    }

    public Page<MeterReading> getAllHistory(Long userId, String type,
                                            LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        return meterReadingRepository.findAllMetersReadingByUserId(userId, type, startDate, endDate, pageable);
    }

}