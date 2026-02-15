package com.codecool.solarwatch.mapper;

import com.codecool.solarwatch.model.City;
import com.codecool.solarwatch.model.dto.CityDTO;
import com.codecool.solarwatch.model.dto.CreateCityDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface CityMapper {
  CityDTO toDTO(City city);

  City toEntity(CreateCityDTO createCityDTO);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "solarEvents", ignore = true)
  void updateCityFromDTO(CityDTO cityDTO, @MappingTarget City city);
}
