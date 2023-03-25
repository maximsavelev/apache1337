package ru.psuti.apache1337.homeowners.controller.user;

import ru.psuti.apache1337.homeowners.dto.CreatedUserDto;
import ru.psuti.apache1337.homeowners.dto.UserDto;
import ru.psuti.apache1337.homeowners.entity.User;
import ru.psuti.apache1337.homeowners.service.OtpService;
import ru.psuti.apache1337.homeowners.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.psuti.apache1337.homeowners.util.UserMapper;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = RegistrationController.REST_URL)
@Slf4j
public class RegistrationController {

    static final String REST_URL = "/rest/users";

    private final OtpService otpService;
    private final UserService userService;

    private final UserMapper userMapper;

    @PostMapping
    public ResponseEntity<UserDto> create(@RequestBody CreatedUserDto createUserTo) {
        log.info("Attempt to create a new user " + createUserTo.getPhone());
        int otp = Integer.parseInt(createUserTo.getOtp());
        if (otp == otpService.getOtp(createUserTo.getPhone())) {

            User createdUser = userMapper.toEntityFromCreatedDto(createUserTo);
            UserDto result = userService.create(createdUser);

            URI uriOfNewUser = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/rest/admin/users" + "/{phone}")
                    .buildAndExpand(result.getPhone()).toUri();
            return ResponseEntity.created(uriOfNewUser).body(result);
        } else {
            throw new BadCredentialsException("Wrong registration password!");
        }
    }

}
