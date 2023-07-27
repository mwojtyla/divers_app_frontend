package com.diver.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class DivingBaseDto {

    public DivingBaseDto() {
        super();
    }

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("localization")
    private String localization;

    @JsonProperty("currencyName")
    private String currencyName;

    @JsonProperty("currencyRate")
    private double currencyRate;

    @JsonProperty("temperature")
    private double temperature;

    @JsonProperty("userId")
    private Long userId;

    public DivingBaseDto(Long id, String name, String localization, String currencyName, double currencyRate, double temperature, Long userId) {
        this.id = id;
        this.name = name;
        this.localization = localization;
        this.currencyName = currencyName;
        this.currencyRate = currencyRate;
        this.temperature = temperature;
        this.userId = userId;
    }
}
