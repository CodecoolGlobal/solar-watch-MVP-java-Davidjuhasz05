package com.codecool.solarwatch.mapper;

import com.codecool.solarwatch.model.SolarEvent;
import com.codecool.solarwatch.model.dto.CreateSolarEventDTO;
import com.codecool.solarwatch.model.dto.SolarEventDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SolarEventMapper {
  @Mapping(source = "city.name", target = "city")
  @Mapping(source = "city.country", target = "country")
  @Mapping(source = "city.state", target = "state")
  SolarEventDTO toDTO(SolarEvent solarEvent);

  @Mapping(target = "city", ignore = true)
  SolarEvent toEntity(CreateSolarEventDTO solarEventsDTO);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "city", ignore = true)
  void updateSolarEventFromDTO(SolarEventDTO solarEventDTO, @MappingTarget SolarEvent solarEvent);
}
