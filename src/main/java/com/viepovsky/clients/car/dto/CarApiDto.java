package com.viepovsky.clients.car.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CarApiDto(@JsonProperty("vehicleModel") String model) {}
