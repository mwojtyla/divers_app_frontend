package com.diver.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;


@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {

    public UserDto() {
        super();
    }
    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("surname")
    private String surname;
    @JsonProperty("dateOfBirth")
    private LocalDate dateOfBirth;
    @JsonProperty("localization")
    private String localization;
    @JsonProperty("email")
    private String email;
    @JsonProperty("login")
    private String login;
    @JsonProperty("password")
    private String password;

    public UserDto(Long id, String name, String surname, LocalDate dateOfBirth, String localization, String email, String login, String password) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.localization = localization;
        this.email = email;
        this.login = login;
        this.password = password;
    }
}
