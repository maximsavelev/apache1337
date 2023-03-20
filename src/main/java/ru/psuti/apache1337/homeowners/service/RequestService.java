package ru.psuti.apache1337.homeowners.service;

import ru.psuti.apache1337.homeowners.dto.RequestDto;
import ru.psuti.apache1337.homeowners.entity.Request;
import ru.psuti.apache1337.homeowners.exception_handling.NotFoundException;
import ru.psuti.apache1337.homeowners.repository.RequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.psuti.apache1337.homeowners.util.RequestMapper;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RequestService {

    private final RequestRepository requestRepository;

    private final RequestMapper requestMapper;

    private final String NOT_FOUND_MESSAGE = "Request with id %d not found";


    public RequestDto findRequestById(Long id) {
        Request result = requestRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_MESSAGE, id)));
        return requestMapper.toEntity(result);
    }


    public Page<RequestDto> findAll(Long status, Long client, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        Page<Request> all = requestRepository.findAllByStatusAndClientAndDateBetween(status, client, startDate, endDate, pageable);
        return all.map(requestMapper::toEntity);
    }


    public RequestDto save(RequestDto requestDto) {

        return requestMapper
                .toEntity(requestRepository.save(requestMapper.toDto(requestDto)));
    }


    public void deleteRequestById(Long id) {
        requestRepository.deleteById(id);
    }

    public boolean isRequestExistsById(Long id) {
        return requestRepository.existsById(id);
    }

}
