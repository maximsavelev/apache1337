package ru.psuti.apache1337.homeowners.controller.user;

import ru.psuti.apache1337.homeowners.security.UserDetailsUserImpl;
import ru.psuti.apache1337.homeowners.dto.UserDto;
import ru.psuti.apache1337.homeowners.entity.User;
import ru.psuti.apache1337.homeowners.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.psuti.apache1337.homeowners.util.UserMapper;

@RestController
@RequestMapping(value = ProfileController.REST_URL)
@RequiredArgsConstructor
public class ProfileController {

    public static final String REST_URL = "/rest/profile";

    private final UserService userService;

    private final UserMapper userMapper;
    @GetMapping()
    public UserDto get(@AuthenticationPrincipal UserDetailsUserImpl userDetailsUserImpl) {
        User userByPhone = userService.findUserByPhone(userDetailsUserImpl.getUsername());
        return userMapper.toUsedDto(userByPhone);
    }

    @GetMapping(value = "/update")
    public UserDto getUserForUpdate(
             @AuthenticationPrincipal UserDetailsUserImpl userDetailsUserImpl) {
        User userByPhone = userService.findUserByPhone(userDetailsUserImpl.getUsername());
        return userMapper.toUsedDto(userByPhone);
    }
    @PutMapping(value = "/update")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody UserDto userTo,
                        @AuthenticationPrincipal UserDetailsUserImpl userDetailsUserImpl) {
        userService.update(userTo);
    }

}
