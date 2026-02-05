package com.codecool.solarwatch.mapper;

import com.codecool.solarwatch.model.SolarEvent;
import com.codecool.solarwatch.model.dto.CreateSolarEventDTO;
import com.codecool.solarwatch.model.dto.SolarEventDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SolarEventMapper {
  SolarEventDTO toDTO(SolarEvent solarEvent);
  SolarEvent toEntity(CreateSolarEventDTO solarEventsDTO);
}
