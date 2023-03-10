package ru.psuti.apache1337.homeowners.util;

import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import ru.psuti.apache1337.homeowners.dto.RequestDto;
import ru.psuti.apache1337.homeowners.entity.Request;
import ru.psuti.apache1337.homeowners.service.AddressService;
import ru.psuti.apache1337.homeowners.service.user.UserService;

import javax.annotation.PostConstruct;

import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

@Component
@RequiredArgsConstructor
public class RequestMapper {
    private final ModelMapper mapper;
    private final UserService userService;

    private final AddressService addressService;

    @PostConstruct
    private void setupMapper() {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT).setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(PRIVATE);
        mapper.createTypeMap(Request.class, RequestDto.class).setPostConverter(toDtoConverter());
        mapper.createTypeMap(RequestDto.class, Request.class).setPostConverter(toEntityConverter());
    }

    public RequestDto toEntity(Request request) {
        return mapper.map(request, RequestDto.class);
    }

    public Request toDto(RequestDto requestDto) {
        return mapper.map(requestDto, Request.class);
    }

    private Converter<RequestDto, Request> toEntityConverter() {
        return context -> {
            RequestDto source = context.getSource();
            Request destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    private Converter<Request, RequestDto> toDtoConverter() {
        return context -> {
            Request source = context.getSource();
            RequestDto destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    private void mapSpecificFields(RequestDto source, Request destination) {
        destination.setClient(userService.findUserById(source.getClientId()));
        destination.setAddress(addressService.findAddressById(source.getAddressId()));
    }

    private void mapSpecificFields(Request source, RequestDto destination) {
        destination.setClientId(source.getClient().getId());
        destination.setAddressId(source.getAddress().getId());
    }

}
