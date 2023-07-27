package com.diver.api.client;

import com.diver.api.config.DiverClientConfig;
import com.diver.dto.EquipmentDto;
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
public class DiverEquipmentClient {
    private final DiverClientConfig diverClientConfig;
    private final RestTemplate restTemplate;
    private static final Logger LOGGER = LoggerFactory.getLogger(DiverEquipmentClient.class);

    public List<EquipmentDto> getEquipments(Long userId) {
        URI url = UriComponentsBuilder.fromHttpUrl(diverClientConfig.getDiverApiEndpoint() + "/equipment/getEquipmentsByUserId/" + userId)
                .build()
                .encode()
                .toUri();
        try {
            EquipmentDto[] response = restTemplate.getForObject(url, EquipmentDto[].class);
            return Optional.ofNullable(response)
                    .map(Arrays::asList)
                    .orElse(Collections.emptyList());
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    public Long getEquipmentIdByNameAndUserId(String name, Long userId) {
        URI url = UriComponentsBuilder.fromHttpUrl(diverClientConfig.getDiverApiEndpoint() + "/equipment/getEquipmentId/" + name + "/" + userId)
                .build()
                .encode()
                .toUri();
        return restTemplate.getForObject(url, Long.class);
    }

    public void createNewEquipment(EquipmentDto equipmentDto) {
        URI url = UriComponentsBuilder.fromHttpUrl(diverClientConfig.getDiverApiEndpoint() + "/equipment")
                .queryParam("id", equipmentDto.getId())
                .queryParam("name", equipmentDto.getName())
                .queryParam("userId", equipmentDto.getUserId())
                .build()
                .encode()
                .toUri();
        restTemplate.postForObject(url, equipmentDto, EquipmentDto.class);
    }

    public void updateEquipment(EquipmentDto equipmentDto) {
        URI url = UriComponentsBuilder.fromHttpUrl(diverClientConfig.getDiverApiEndpoint() + "/equipment")
                .queryParam("id", equipmentDto.getId())
                .queryParam("name", equipmentDto.getName())
                .queryParam("userId", equipmentDto.getUserId())
                .build()
                .encode()
                .toUri();
        restTemplate.put(url, equipmentDto);
    }

    public void deleteEquipment(Long equipmentId) {
        URI url = UriComponentsBuilder.fromHttpUrl(diverClientConfig.getDiverApiEndpoint() + "/equipment" + "/" + equipmentId)
                .build()
                .encode()
                .toUri();
        restTemplate.delete(url);
    }
}
