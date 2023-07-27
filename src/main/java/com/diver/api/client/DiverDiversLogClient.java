package com.diver.api.client;

import com.diver.api.config.DiverClientConfig;
import com.diver.dto.DiversLogDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DiverDiversLogClient {
    private final DiverClientConfig diverClientConfig;
    private final RestTemplate restTemplate;
    private static final Logger LOGGER = LoggerFactory.getLogger(DiverDiversLogClient.class);

    public List<DiversLogDto> getDiversLogsByUserId(Long userId) {
        URI url = UriComponentsBuilder.fromHttpUrl(diverClientConfig.getDiverApiEndpoint() + "/diverslog/" + userId)
                .build()
                .encode()
                .toUri();
        try {
            DiversLogDto[] response = restTemplate.getForObject(url, DiversLogDto[].class);
            return Optional.ofNullable(response)
                    .map(Arrays::asList)
                    .orElse(Collections.emptyList());
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    public List<DiversLogDto> getDiversLogByLocalizationAndUserId(String localization, Long userId) {
        URI url = UriComponentsBuilder.fromHttpUrl(diverClientConfig.getDiverApiEndpoint() + "/diverslog/localization/" + localization + "/" + userId)
                .build()
                .encode()
                .toUri();
        try {
            DiversLogDto[] response = restTemplate.getForObject(url, DiversLogDto[].class);
            return Optional.ofNullable(response)
                    .map(Arrays::asList)
                    .orElse(Collections.emptyList());
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    public List<DiversLogDto> getDiversLogByDepthAndUserId(double depth, Long userId) {
        URI url = UriComponentsBuilder.fromHttpUrl(diverClientConfig.getDiverApiEndpoint() + "/diverslog/depth/" + depth + "/" + userId)
                .build()
                .encode()
                .toUri();
        try {
            DiversLogDto[] response = restTemplate.getForObject(url, DiversLogDto[].class);
            return Optional.ofNullable(response)
                    .map(Arrays::asList)
                    .orElse(Collections.emptyList());
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    public void createNewDiversLog(DiversLogDto diversLogDto) {
        URI url = UriComponentsBuilder.fromHttpUrl(diverClientConfig.getDiverApiEndpoint() + "/diverslog")
                .queryParam("dateOfDiving", diversLogDto.getDateOfDiving())
                .queryParam("localization", diversLogDto.getLocalization())
                .queryParam("visibility", diversLogDto.getVisibility())
                .queryParam("airTemperature", diversLogDto.getAirTemperature())
                .queryParam("surfaceTemperature", diversLogDto.getSurfaceTemperature())
                .queryParam("bottomTemperature", diversLogDto.getBottomTemperature())
                .queryParam("weight", diversLogDto.getWeight())
                .queryParam("depth", diversLogDto.getDepth())
                .queryParam("timeOfDiving", diversLogDto.getTimeOfDiving())
                .queryParam("conditions", diversLogDto.getConditions())
                .queryParam("timeIn", diversLogDto.getTimeIn())
                .queryParam("timeOut", diversLogDto.getTimeOut())
                .queryParam("airUsed", diversLogDto.getAirUsed())
                .queryParam("userId", diversLogDto.getUserId())
                .build()
                .encode()
                .toUri();
        restTemplate.postForObject(url, diversLogDto, DiversLogDto.class);
    }

    public void updateDiversLog(DiversLogDto diversLogDto) {
        URI url = UriComponentsBuilder.fromHttpUrl(diverClientConfig.getDiverApiEndpoint() + "/diverslog")
                .queryParam("id", diversLogDto.getId())
                .queryParam("dateOfDiving", diversLogDto.getDateOfDiving())
                .queryParam("localization", diversLogDto.getLocalization())
                .queryParam("visibility", diversLogDto.getVisibility())
                .queryParam("airTemperature", diversLogDto.getAirTemperature())
                .queryParam("surfaceTemperature", diversLogDto.getSurfaceTemperature())
                .queryParam("bottomTemperature", diversLogDto.getBottomTemperature())
                .queryParam("weight", diversLogDto.getWeight())
                .queryParam("depth", diversLogDto.getDepth())
                .queryParam("timeOfDiving", diversLogDto.getTimeOfDiving())
                .queryParam("conditions", diversLogDto.getConditions())
                .queryParam("timeIn", diversLogDto.getTimeIn())
                .queryParam("timeOut", diversLogDto.getTimeOut())
                .queryParam("airUsed", diversLogDto.getAirUsed())
                .queryParam("userId", diversLogDto.getUserId())
                .build()
                .encode()
                .toUri();
        restTemplate.put(url, diversLogDto);
    }

    public void deleteDiversLog(Long diversLogId) {
        URI url = UriComponentsBuilder.fromHttpUrl(diverClientConfig.getDiverApiEndpoint() + "/diverslog/" + diversLogId)
                .build()
                .encode()
                .toUri();
        restTemplate.delete(url);
    }
}
