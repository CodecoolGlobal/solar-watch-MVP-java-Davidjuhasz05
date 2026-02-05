package com.codecool.solarwatch.service;

import com.codecool.solarwatch.exception.CityExistsException;
import com.codecool.solarwatch.exception.SolarEventExistsException;
import com.codecool.solarwatch.mapper.CityMapper;
import com.codecool.solarwatch.mapper.SolarEventMapper;
import com.codecool.solarwatch.model.City;
import com.codecool.solarwatch.model.SolarEvent;
import com.codecool.solarwatch.model.dto.CityDTO;
import com.codecool.solarwatch.model.dto.CreateCityDTO;
import com.codecool.solarwatch.model.dto.CreateSolarEventDTO;
import com.codecool.solarwatch.model.dto.SolarEventDTO;
import com.codecool.solarwatch.repository.CityRepository;
import com.codecool.solarwatch.repository.SolarEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminService {

  private final SolarEventRepository solarEventRepository;
  private final CityRepository cityRepository;
  private final CityMapper cityMapper;
  private final SolarEventMapper solarEventMapper;

  public CityDTO createCity(CreateCityDTO city) {
    if (cityRepository.existsByName(city.name())) {
      throw new CityExistsException(city.name());
    }

    City newCity = cityMapper.toEntity(city);
    City savedCity = cityRepository.save(newCity);
    return cityMapper.toDTO(savedCity);
  }

  public SolarEventDTO createSolarEvent(CreateSolarEventDTO solarEvent) {
    if (solarEventRepository.existsByCityNameAndDate(solarEvent.city(), solarEvent.date())) {
      throw new SolarEventExistsException(solarEvent.city(), solarEvent.date());
    }

    SolarEvent newSolarEvent = solarEventMapper.toEntity(solarEvent);
    SolarEvent savedSolarEvent = solarEventRepository.save(newSolarEvent);
    return solarEventMapper.toDTO(savedSolarEvent);
  }

}
