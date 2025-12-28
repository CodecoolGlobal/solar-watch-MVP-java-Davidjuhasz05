package com.codecool.solarwatch.service;

import com.codecool.solarwatch.client.GeoClient;
import com.codecool.solarwatch.client.SolarEventsClient;
import com.codecool.solarwatch.client.response.GeoResponse;
import com.codecool.solarwatch.exception.ResourceNotFoundException;
import com.codecool.solarwatch.model.City;
import com.codecool.solarwatch.client.response.SolarEventsResponse;
import com.codecool.solarwatch.model.SolarEventsDTO;
import com.codecool.solarwatch.model.SolarEvent;
import com.codecool.solarwatch.repository.CityRepository;
import com.codecool.solarwatch.repository.SolarEventRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class SolarEventsService {

  @Value("${API_KEY}")
  private String apiKey;

  private final SolarEventsClient solarEventsClient;
  private final GeoClient geoClient;
  private final CityRepository cityRepository;
  private final SolarEventRepository solarEventRepository;

  public SolarEventsService(SolarEventsClient solarEventsClient, GeoClient geoClient, CityRepository cityRepository, SolarEventRepository solarEventRepository) {
    this.solarEventsClient = solarEventsClient;
    this.geoClient = geoClient;
    this.cityRepository = cityRepository;
    this.solarEventRepository = solarEventRepository;
  }

  public SolarEventsDTO getSolarEvents(@RequestParam String cityName, @RequestParam LocalDate date) {
    log.info("Starting solar event retrieval for city: '{}', date: {}", cityName, date);
    log.debug("Checking database for city: '{}', date: {}", cityName, date);

    City city;

    var cityOptional = cityRepository.findByName(cityName);
    if (cityOptional.isPresent()) {
      log.info("City '{}' found in database. Cache HIT", cityName);
      city = cityOptional.get();
    } else {
      log.info("City '{}' not found in database. Cache MISS", cityName);
      city = fetchAndSaveCity(cityName);
    }

    log.debug("Checking database for solar event for '{}' on {}", date, cityName);

    SolarEvent solarEvent;

    var solarOptional = solarEventRepository.findByCityAndDate(city, date);
    if (solarOptional.isPresent()) {
      log.info("Solar events for '{}' on {} found in database. Cache HIT", cityName, date);
      solarEvent = solarOptional.get();
    } else {
      log.info("Solar events for '{}' on {} not found in database. Cache MISS",  cityName, date);
      solarEvent = fetchAndSaveSolarEvents(city, date);
    }

    return mapToDTO(solarEvent);
  }

  private City fetchAndSaveCity(String cityName) {
    log.info("City '{}' not found in database. Fetching from API...", cityName);

    List<GeoResponse> geoResponse;

    try {
      log.debug("Resolving coordinates for city: '{}'", cityName);
      long startTime = System.currentTimeMillis();

      geoResponse = geoClient.fetchCityData(cityName, 1, apiKey);

      long duration = System.currentTimeMillis() - startTime;
      log.info("Successfully retrieved coordinates from GeoClient in {} ms", duration);

    } catch (Exception e) {
      log.error("Failed to communicate with GeoClient for city: '{}'", cityName, e);
      throw e;
    }

    if(geoResponse.isEmpty()) {
      log.warn("GeoClient returned zero results for city: '{}'. Throwing 404", cityName);
      throw new ResourceNotFoundException("City not found: " + cityName);
    }

    GeoResponse city = geoResponse.getFirst();
    log.debug("Resolved city: '{}' to Lat: {}, Lon: {}", city.name(), city.lat(), city.lon());

    City newEntity = City.builder()
            .name(city.name())
            .latitude(city.lat())
            .longitude(city.lon())
            .country(city.country())
            .state(city.state())
            .build();

    log.info("Saving city '{}' to database", cityName);
    return cityRepository.save(newEntity);
  }

  private SolarEvent fetchAndSaveSolarEvents(City city, LocalDate date) {
    log.info("Solar events for '{}' on {} not found in database. Fetching from API...", city.getName(), date);

    SolarEventsResponse response;

    try {
      log.debug("Fetching Solar events for city: '{}'", city.getName());
      long startTime = System.currentTimeMillis();

      response = solarEventsClient.fetchSolarEvents(
              city.getLatitude(),
              city.getLongitude(),
              date
      );

      long duration = System.currentTimeMillis() - startTime;
      log.info("Successfully retrieved solar event data for city: '{}' in {} ms", city.getName(), duration);

    } catch (Exception e) {
      log.error("Failed to communicate with SolarEventsClient for city: '{}', Lat: {}, Lon: {}",
              city.getName(), city.getLatitude(), city.getLongitude(), e);
      throw e;
    }

    SolarEvent newEntity = SolarEvent.builder()
            .city(city)
            .date(date)
            .sunrise(response.results().sunrise())
            .sunset(response.results().sunset())
            .timezone(response.tzid())
            .build();

    log.info("Saving solar events for '{}' on {} to database", city.getName(), date);
    return solarEventRepository.save(newEntity);
  }

  private SolarEventsDTO mapToDTO(SolarEvent entity) {
    return new SolarEventsDTO(
            entity.getCity().getName(),
            entity.getCity().getCountry(),
            entity.getCity().getState(),
            entity.getDate(),
            entity.getSunrise(),
            entity.getSunset(),
            entity.getTimezone()
    );
  }

}
