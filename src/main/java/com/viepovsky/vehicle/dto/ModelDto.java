package com.viepovsky.vehicle.dto;

public record ModelDto(Long modelId, String modelName, MakeDto vehicleMake, String type) {}
