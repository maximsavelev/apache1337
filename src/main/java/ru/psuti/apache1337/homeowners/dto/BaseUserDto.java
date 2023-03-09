package ru.psuti.apache1337.homeowners.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;
@Data
@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, isGetterVisibility = NONE, setterVisibility = NONE)
public abstract class BaseUserDto  {

    private Integer id;
    private  String phone;
    private  String email;
    private  String firstName;
    private  String secondName;
    private  String patronymic;
    private  String city;
    private  String street;
    private  String house;
    private  String building;
    private  String apartment;
    private  String roles;


}
