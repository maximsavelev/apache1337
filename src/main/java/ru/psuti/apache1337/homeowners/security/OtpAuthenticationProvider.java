package ru.psuti.apache1337.homeowners.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import ru.psuti.apache1337.homeowners.entity.User;
import ru.psuti.apache1337.homeowners.service.OtpService;
import ru.psuti.apache1337.homeowners.service.user.UserService;

@Component
@RequiredArgsConstructor
@Slf4j
public class OtpAuthenticationProvider implements AuthenticationProvider {

    private final UserService userService;
    private final OtpService otpService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userName = authentication.getName();
        String password = authentication.getCredentials().toString();
        User user = userService.findUserByPhone(userName);
        String optPass = String.valueOf(otpService.getOtp(userName));
        if (!password.equals(optPass)) {
            log.info("Неудачная попытка авторизации " + userName + " .");
            throw new BadCredentialsException("wrong password");
        }
        log.info("User " + userName + " authorized .");
        UserDetailsUserImpl principal = new UserDetailsUserImpl(user);
        return new UsernamePasswordAuthenticationToken(
                principal, password, principal.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
