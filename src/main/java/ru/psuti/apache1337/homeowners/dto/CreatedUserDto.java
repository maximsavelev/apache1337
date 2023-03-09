package ru.psuti.apache1337.homeowners.dto;

import lombok.Data;

@Data
public class CreatedUserDto extends BaseUserDto  {
    private  String otp;

}
