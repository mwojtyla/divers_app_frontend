package com.diver.api.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
@Getter
public class DiverClientConfig {
    @Value("${diver.api.endpoint.prod}")
    private String diverApiEndpoint;
}
