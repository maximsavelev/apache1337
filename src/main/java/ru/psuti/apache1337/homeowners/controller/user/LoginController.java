package ru.psuti.apache1337.homeowners.controller.user;

import ru.psuti.apache1337.homeowners.entity.User;
import ru.psuti.apache1337.homeowners.service.OtpService;
import ru.psuti.apache1337.homeowners.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LoginController {
    private final UserService userService;
    private final OtpService otpService;

    @PostMapping("/onetimecode")
    public HashMap<String, String> getOneTimePassword(@RequestParam(value = "username") String phone) {
        log.info("Authorization attempt " + phone + ".");
        User userByPhone = userService.findUserByPhone(phone);
        System.out.println(userByPhone);
        int oneTimePassword = otpService.generateOTP(phone);
        HashMap<String, String> map = new HashMap<>();
        map.put("username", String.valueOf(phone));
        map.put("smsPassword", String.valueOf(oneTimePassword));
        return map;
    }

    @PostMapping("/registration-otp")
    public HashMap<String, String> getRegistrationOneTimePassword(@RequestParam(value = "username") String phone) {
        int oneTimePassword = otpService.generateOTP(phone);
        HashMap<String, String> map = new HashMap<>();
        map.put("smsPassword", String.valueOf(oneTimePassword));
        return map;
    }
}
