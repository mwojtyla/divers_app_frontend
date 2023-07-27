package com.diver.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDtoToAuthorised {
    @JsonProperty("login")
    private String login;
    @JsonProperty("password")
    private String password;
}
