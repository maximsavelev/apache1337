package ru.psuti.apache1337.homeowners.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER,
    ADMIN;


    @Override
    public String getAuthority() {
        return "ROLE_" + name();
    }
}
