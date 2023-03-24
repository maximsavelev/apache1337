package ru.psuti.apache1337.homeowners.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.psuti.apache1337.homeowners.security.UserDetailsUserImpl;
import ru.psuti.apache1337.homeowners.dto.MeterDto;
import ru.psuti.apache1337.homeowners.dto.MeterReadingDto;
import ru.psuti.apache1337.homeowners.entity.MeterReading;
import ru.psuti.apache1337.homeowners.exception_handling.IncorrectMeterReadingException;
import ru.psuti.apache1337.homeowners.exception_handling.RepeatedMeterNameException;
import ru.psuti.apache1337.homeowners.service.MeterReadingService;
import ru.psuti.apache1337.homeowners.service.MeterService;
import ru.psuti.apache1337.homeowners.service.user.UserService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = MeterController.METER_CONTROLLER_URL)
@RequiredArgsConstructor
@Slf4j

public class MeterController {
    static final String METER_CONTROLLER_URL = "/rest/counters";
    private final MeterService meterService;
    private final MeterReadingService meterReadingService;
    private final UserService userService;

    @GetMapping()
    public ResponseEntity<List<MeterReadingDto>> getAllLatestMeterReading(@AuthenticationPrincipal UserDetailsUserImpl user) {
        List<MeterDto> allMeters = meterService
                .findAllMeterByUserId(userService.findUserByPhone(user.getUsername()).getId());
        List<MeterReadingDto> latestMeterReadingByUserMeters = meterReadingService.findLatestMeterReadingByUserMeters(allMeters);
        return new ResponseEntity<>(latestMeterReadingByUserMeters, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> addNewMeter(@AuthenticationPrincipal UserDetailsUserImpl user,
                                         @RequestBody MeterDto meterDto) {
        for (MeterDto existedMeter : meterService.findAllMeterByUserId(userService.findUserByPhone(user.getUsername()).getId())) {
            if ((meterDto.getName().trim()).equals(existedMeter.getName().trim())) {
                log.info("Попытка добавить существующий счетчик " + meterDto.getName() + ".");
                throw new RepeatedMeterNameException("Счётчик с таким именем уже существует");
            }
        }
        MeterReadingDto meterReadingDto = new MeterReadingDto();
        meterReadingDto.setMeter(meterDto);
        meterReadingDto.setValue(0);
        meterReadingDto.setDateTime(LocalDateTime.now());
        meterReadingService.save(meterReadingDto);
        log.info("Пользователь " + user.getUsername() + " добавил новый счетчик " + meterDto.getName() + ".");
        return new ResponseEntity<>(meterReadingDto, HttpStatus.OK);
    }

    @PutMapping()
    public List<MeterReadingDto> addMeterReading(@AuthenticationPrincipal UserDetailsUserImpl user,
                                                 @RequestBody List<MeterReadingDto> meterReadingsDto) {
        if (!meterReadingsDto.isEmpty()) {
            for (MeterReadingDto meterReadingDto : meterReadingsDto) {
                if (meterReadingDto.getValue() > meterReadingService.findLatestMetersReadingByMeter(meterReadingDto.getMeter()).getValue()) {
                    meterReadingDto.setDateTime(LocalDateTime.now());
                    MeterReadingDto save = meterReadingService.save(meterReadingDto);
                    log.info("Пользователь " + user.getUsername() + " отправил новые значение счетчика.");
                } else if (meterReadingDto.getValue() < meterReadingService.findLatestMetersReadingByMeter(meterReadingDto.getMeter())
                        .getValue()) {
                    log.info("Попытка внести меньшее значение в счетчик пользователем " + user.getUsername() + ".");
                    throw new IncorrectMeterReadingException(
                            "Введённое значение " + meterReadingDto.getValue() + " меньше предыдущего");
                }
            }
        }
        return meterReadingService.findLatestMeterReadingByUserMeters(meterService
                .findAllMeterByUserId(userService.findUserByPhone(user.getUsername()).getId()));
    }

    @GetMapping("/history")
    public List<MeterReading> getAllMeterReadingHistory(
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "startDate", required = false) LocalDateTime startDate,
            @RequestParam(value = "endDate", required = false) LocalDateTime endDate,
            @RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize,
            @AuthenticationPrincipal UserDetailsUserImpl user) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("id").descending());
        return meterReadingService
                .getAllHistory(userService.findUserByPhone(user.getUsername()).getId(), type, startDate, endDate, pageable)
                .getContent();
    }


}