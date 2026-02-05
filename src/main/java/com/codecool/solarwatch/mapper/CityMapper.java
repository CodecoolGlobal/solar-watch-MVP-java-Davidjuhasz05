package com.codecool.solarwatch.mapper;

import com.codecool.solarwatch.model.City;
import com.codecool.solarwatch.model.dto.CityDTO;
import com.codecool.solarwatch.model.dto.CreateCityDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CityMapper {
  CityDTO toDTO(City city);
  City toEntity(CreateCityDTO createCityDTO);
}
