package com.diver.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class DiversLogDto {

    public DiversLogDto() {
        super();
    }
    @JsonProperty("id")
    private Long id;
    @JsonProperty("dateOfDiving")
    private LocalDate dateOfDiving;
    @JsonProperty("localization")
    private String localization;
    @JsonProperty("visibility")
    private int visibility;
    @JsonProperty("airTemperature")
    private double airTemperature;
    @JsonProperty("surfaceTemperature")
    private double surfaceTemperature;
    @JsonProperty("bottomTemperature")
    private double bottomTemperature;
    @JsonProperty("weight")
    private double weight;

    @JsonProperty("depth")
    private double depth;
    @JsonProperty("timeOfDiving")
    private int timeOfDiving;
    @JsonProperty("conditions")
    private String conditions;
    @JsonProperty("timeIn")
    private LocalTime timeIn;
    @JsonProperty("timeOut")
    private LocalTime timeOut;
    @JsonProperty("airUsed")
    private int airUsed;
    @JsonProperty("userId")
    private Long userId;

    public DiversLogDto(Long id, LocalDate dateOfDiving, String localization, int visibility, double airTemperature, double surfaceTemperature, double bottomTemperature, double weight, double depth, int timeOfDiving, String conditions, LocalTime timeIn, LocalTime timeOut, int airUsed, Long userId) {
        this.id = id;
        this.dateOfDiving = dateOfDiving;
        this.localization = localization;
        this.visibility = visibility;
        this.airTemperature = airTemperature;
        this.surfaceTemperature = surfaceTemperature;
        this.bottomTemperature = bottomTemperature;
        this.weight = weight;
        this.depth = depth;
        this.timeOfDiving = timeOfDiving;
        this.conditions = conditions;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
        this.airUsed = airUsed;
        this.userId = userId;
    }
}
