package com.codecool.solarwatch.service;

import com.codecool.solarwatch.exception.CityExistsException;
import com.codecool.solarwatch.exception.ResourceNotFoundException;
import com.codecool.solarwatch.exception.SolarEventExistsException;
import com.codecool.solarwatch.mapper.CityMapper;
import com.codecool.solarwatch.mapper.SolarEventMapper;
import com.codecool.solarwatch.model.City;
import com.codecool.solarwatch.model.SolarEvent;
import com.codecool.solarwatch.model.dto.CityDTO;
import com.codecool.solarwatch.model.dto.CreateCityDTO;
import com.codecool.solarwatch.model.dto.CreateSolarEventDTO;
import com.codecool.solarwatch.model.dto.SolarEventDTO;
import com.codecool.solarwatch.model.user.Role;
import com.codecool.solarwatch.model.user.SolarWatchUser;
import com.codecool.solarwatch.repository.CityRepository;
import com.codecool.solarwatch.repository.SolarEventRepository;
import com.codecool.solarwatch.repository.UserRepository;
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
  private final UserRepository userRepository;

  public CityDTO createCity(CreateCityDTO cityDTO) {
    if (cityRepository.existsByName(cityDTO.name())) {
      throw new CityExistsException(cityDTO.name());
    }

    City newCity = cityMapper.toEntity(cityDTO);
    City savedCity = cityRepository.save(newCity);
    return cityMapper.toDTO(savedCity);
  }

  public SolarEventDTO createSolarEvent(CreateSolarEventDTO solarEventDTO) {
    if (solarEventRepository.existsByCityNameAndDate(solarEventDTO.city(), solarEventDTO.date())) {
      throw new SolarEventExistsException(solarEventDTO.city(), solarEventDTO.date());
    }

    SolarEvent newSolarEvent = solarEventMapper.toEntity(solarEventDTO);

    City city = cityRepository.findByNameIgnoreCase(solarEventDTO.city())
            .orElseThrow(() -> new ResourceNotFoundException(solarEventDTO.city()));
    newSolarEvent.setCity(city);

    SolarEvent savedSolarEvent = solarEventRepository.save(newSolarEvent);
    return solarEventMapper.toDTO(savedSolarEvent);
  }

  public CityDTO updateCity(CityDTO cityDTO, Long id) {
    City city = cityRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(id.toString()));

    cityMapper.updateCityFromDTO(cityDTO, city);
    City savedCity = cityRepository.save(city);
    return cityMapper.toDTO(savedCity);
  }

  // TODO: Refactor city retrieval to fetch from external API if not found in DB. Needs separate CityService class.
  public SolarEventDTO updateSolarEvent(SolarEventDTO solarEventDTO, Long id) {
    SolarEvent solarEvent = solarEventRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(id.toString()));

    solarEventMapper.updateSolarEventFromDTO(solarEventDTO, solarEvent);

    if (solarEventDTO.city() != null &&
            !solarEventDTO.city().equals(solarEvent.getCity().getName())) {
      City city = cityRepository.findByNameIgnoreCase(solarEventDTO.city())
              .orElseThrow(() -> new ResourceNotFoundException(solarEventDTO.city()));
      solarEvent.setCity(city);
    }

    SolarEvent savedSolarEvent = solarEventRepository.save(solarEvent);
    return solarEventMapper.toDTO(savedSolarEvent);
  }

  public void deleteCity(Long id) {
    if (!cityRepository.existsById(id)) {
      throw new ResourceNotFoundException(id.toString()); //
    }
    cityRepository.deleteById(id);
  }

  public void deleteSolarEvent(Long id) {
    if (!solarEventRepository.existsById(id)) {
      throw new ResourceNotFoundException(id.toString());
    }
    solarEventRepository.deleteById(id);
  }

  public void changeRole(String username, Role newRole) {
    SolarWatchUser user = userRepository.findByUsernameIgnoreCase(username)
            .orElseThrow(() -> new ResourceNotFoundException(username));
    user.getRoles().add(newRole);
    userRepository.save(user);
  }

}
