package ru.psuti.apache1337.homeowners.security;

import ru.psuti.apache1337.homeowners.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserDetailsUserImpl extends org.springframework.security.core.userdetails.User {

    private static final long serialVersionUID = 1L;

    public UserDetailsUserImpl(User user) {
        super(user.getPhone(),
                UUID.randomUUID().toString(), true,
                true, true, true,
                user.getRoles());
    }

}



