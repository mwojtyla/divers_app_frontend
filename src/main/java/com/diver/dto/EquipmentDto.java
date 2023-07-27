package com.diver.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EquipmentDto {

    public EquipmentDto() {
        super();
    }

    private Long id;
    private String name;
    private Long userId;

    public EquipmentDto(Long id, String name, Long userId) {
        this.id = id;
        this.name = name;
        this.userId = userId;
    }
}
