package ru.psuti.apache1337.homeowners.service;

import org.modelmapper.ModelMapper;
import ru.psuti.apache1337.homeowners.dto.MeterDto;
import ru.psuti.apache1337.homeowners.entity.Meter;
import ru.psuti.apache1337.homeowners.repository.meter.MeterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.psuti.apache1337.homeowners.service.user.UserService;
import ru.psuti.apache1337.homeowners.util.MeterMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MeterService {

    private final MeterRepository meterRepository;

    private final UserService userService;

    private final MeterMapper meterMapper;


    public List<MeterDto> findAllMeterByUserId(Long userId) {
        return meterRepository.findAllByUser(userService.findUserById(userId))
                .stream()
                .map(meterMapper::toDto).collect(Collectors.toList());
    }
}