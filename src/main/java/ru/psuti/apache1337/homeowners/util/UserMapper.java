package ru.psuti.apache1337.homeowners.util;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import ru.psuti.apache1337.homeowners.dto.CreatedUserDto;
import ru.psuti.apache1337.homeowners.dto.UserDto;
import ru.psuti.apache1337.homeowners.entity.Address;
import ru.psuti.apache1337.homeowners.entity.User;

import javax.annotation.PostConstruct;

import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final ModelMapper mapper;


    @PostConstruct
    private void setupMapper() {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT).setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(PRIVATE);
        mapper.createTypeMap(User.class, UserDto.class);
        mapper.createTypeMap(UserDto.class, User.class);
    }


    public User toEntityFromDto(UserDto userDto){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(
                new PropertyMap<UserDto, User>() {
                    @Override
                    protected void configure() {
                        using(ctx -> fillAddress(
                                ((UserDto) ctx.getSource()).getCity(),
                                ((UserDto) ctx.getSource()).getStreet(),
                                ((UserDto) ctx.getSource()).getHouse(),
                                ((UserDto) ctx.getSource()).getBuilding(),
                                ((UserDto) ctx.getSource()).getApartment())
                        )
                                .map(source, destination.getAddress());
                    }
                });
        return modelMapper.map(userDto, User.class);
    }


    public UserDto toUsedDto(User user){

        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        mapper.typeMap(User.class, UserDto.class)
                .addMappings(m -> m.map(src -> src.getAddress().getCity(), UserDto::setCity))
                .addMappings(m -> m.map(src -> src.getAddress().getStreet(), UserDto::setStreet))
                .addMappings(m -> m.map(src -> src.getAddress().getApartment(), UserDto::setApartment))
                .addMappings(m -> m.map(src -> src.getAddress().getBuilding(), UserDto::setBuilding))
                .addMappings(m -> m.map(src -> src.getAddress().getHouse(), UserDto::setHouse));
        return   mapper.map(user, UserDto.class);
    }
    public User toEntityFromCreatedDto(CreatedUserDto createdUserDto){
        User user = mapper.map(createdUserDto, User.class);
        Address address = new Address(createdUserDto.getCity(),
                createdUserDto.getStreet(),createdUserDto.getHouse(),createdUserDto.getBuilding(),createdUserDto.getApartment());
        user.setAddress(address);
        return user;
    }


    private Address fillAddress(String city, String street, String house, String building, String apartment) {
        return   new Address(city,street,house,building,apartment);

    }
}
