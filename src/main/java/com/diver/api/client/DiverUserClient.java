package com.diver.api.client;

import com.diver.api.config.DiverClientConfig;
import com.diver.dto.UserDto;
import com.diver.dto.UserDtoToAuthorised;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class DiverUserClient {

    private final DiverClientConfig diverClientConfig;
    private final RestTemplate restTemplate;

    public boolean isAuthorised(UserDtoToAuthorised userDtoToAuthorised) {
        URI url = UriComponentsBuilder.fromHttpUrl(diverClientConfig.getDiverApiEndpoint() + "/user/authorised/" + userDtoToAuthorised.getLogin() + "/" + userDtoToAuthorised.getPassword())
                .build()
                .encode()
                .toUri();
        try {
            return restTemplate.getForObject(url, Boolean.class);
        } catch (RestClientException e) {
            return false;
        }
    }

    public boolean isLoginAvailable(String login) {
        URI url = UriComponentsBuilder.fromHttpUrl(diverClientConfig.getDiverApiEndpoint() + "/user/loginVerification/" + login)
                .build()
                .encode()
                .toUri();
        return restTemplate.getForObject(url, Boolean.class);
    }

    public UserDto getUser(Long userId) {
        URI url = UriComponentsBuilder.fromHttpUrl(diverClientConfig.getDiverApiEndpoint() + "/user" + "/" + userId)
                .build()
                .encode()
                .toUri();
        return restTemplate.getForObject(url, UserDto.class);
    }

    public Long getUserIdByLoginAndPassword(UserDtoToAuthorised userDtoToAuthorised) {
        URI url = UriComponentsBuilder.fromHttpUrl(diverClientConfig.getDiverApiEndpoint() + "/user/getUserId/" + userDtoToAuthorised.getLogin() + "/" + userDtoToAuthorised.getPassword())
                .build()
                .encode()
                .toUri();
        return restTemplate.getForObject(url, Long.class);
    }

    public void createNewUser(UserDto userDto) {
        URI url = UriComponentsBuilder.fromHttpUrl(diverClientConfig.getDiverApiEndpoint() + "/user")
                .queryParam("id", userDto.getId())
                .queryParam("name", userDto.getName())
                .queryParam("surname", userDto.getSurname())
                .queryParam("dateOfBirth", userDto.getDateOfBirth())
                .queryParam("localization", userDto.getLocalization())
                .queryParam("email", userDto.getEmail())
                .queryParam("login", userDto.getLogin())
                .queryParam("password", userDto.getLogin())
                .build()
                .encode()
                .toUri();
        restTemplate.postForObject(url, userDto, UserDto.class);
    }

    public void updateUser(UserDto userDto) {
        URI url = UriComponentsBuilder.fromHttpUrl(diverClientConfig.getDiverApiEndpoint() + "/user")
                .queryParam("id", userDto.getId())
                .queryParam("name", userDto.getName())
                .queryParam("surname", userDto.getSurname())
                .queryParam("dateOfBirth", userDto.getDateOfBirth())
                .queryParam("localization", userDto.getLocalization())
                .queryParam("email", userDto.getEmail())
                .queryParam("login", userDto.getLogin())
                .queryParam("password", userDto.getLogin())
                .build()
                .encode()
                .toUri();
        restTemplate.put(url, userDto);
    }

    public void deleteUser(Long userId) {
        URI url = UriComponentsBuilder.fromHttpUrl(diverClientConfig.getDiverApiEndpoint() + "/user" + "/" + userId)
                .build()
                .encode()
                .toUri();
        restTemplate.delete(url);
    }
}
