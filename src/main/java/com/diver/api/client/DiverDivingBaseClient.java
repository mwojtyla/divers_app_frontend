package com.diver.api.client;

import com.diver.api.config.DiverClientConfig;
import com.diver.dto.DivingBaseDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;

@Component
@RequiredArgsConstructor
public class DiverDivingBaseClient {
    private final DiverClientConfig diverClientConfig;
    private final RestTemplate restTemplate;
    private static final Logger LOGGER = LoggerFactory.getLogger(DiverDivingBaseClient.class);

    public List<DivingBaseDto> getDivingBasesByUserId(Long userId){
        URI url = UriComponentsBuilder.fromHttpUrl(diverClientConfig.getDiverApiEndpoint() + "/divingbase/" + userId)
                .build()
                .encode()
                .toUri();

        try {
            DivingBaseDto[] response = restTemplate.getForObject(url, DivingBaseDto[].class);
            return Optional.ofNullable(response)
                    .map(Arrays::asList)
                    .orElse(Collections.emptyList());
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    public void createNewDivingBase(DivingBaseDto divingBaseDto){
        URI url = UriComponentsBuilder.fromHttpUrl(diverClientConfig.getDiverApiEndpoint() + "/divingbase")
                .queryParam("name", divingBaseDto.getName())
                .queryParam("localization", divingBaseDto.getLocalization())
                .queryParam("currencyName", divingBaseDto.getCurrencyName())
                .queryParam("userId", divingBaseDto.getUserId())
                .build()
                .encode()
                .toUri();
        restTemplate.postForObject(url, divingBaseDto, DivingBaseDto.class);
    }

    public void updateDivingBaseDto (DivingBaseDto divingBaseDto){
        URI url = UriComponentsBuilder.fromHttpUrl(diverClientConfig.getDiverApiEndpoint() + "/divingbase")
                .queryParam("id", divingBaseDto.getId())
                .queryParam("name", divingBaseDto.getName())
                .queryParam("localization", divingBaseDto.getLocalization())
                .queryParam("currencyName", divingBaseDto.getCurrencyName())
                .queryParam("userId", divingBaseDto.getUserId())
                .build()
                .encode()
                .toUri();
        restTemplate.put(url, divingBaseDto);
    }

    public void deleteDivingBase(Long divingBaseId){
        URI url = UriComponentsBuilder.fromHttpUrl(diverClientConfig.getDiverApiEndpoint() + "/divingbase"  + "/" + divingBaseId)
                .build()
                .encode()
                .toUri();
        restTemplate.delete(url);
    }
}

