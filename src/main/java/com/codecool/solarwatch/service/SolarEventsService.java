package com.codecool.solarwatch.service;

import com.codecool.solarwatch.client.GeoClient;
import com.codecool.solarwatch.client.SolarEventsClient;
import com.codecool.solarwatch.client.response.CityCoordinates;
import com.codecool.solarwatch.client.response.SolarEventsResponse;
import com.codecool.solarwatch.exception.ResourceNotFoundException;
import com.codecool.solarwatch.model.SolarEventsDTO;
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

  public SolarEventsService(SolarEventsClient solarEventsClient, GeoClient geoClient) {
    this.solarEventsClient = solarEventsClient;
    this.geoClient = geoClient;
  }

  public SolarEventsDTO getSolarEvents(@RequestParam String city, @RequestParam LocalDate date) {
    log.info("Starting solar event retrieval for city: '{}', date: {}", city, date);
    log.debug("Resolving coordinates for city: '{}'", city);

    List<CityCoordinates> geoResponse;

    try {
      long startTime = System.currentTimeMillis();

      geoResponse = geoClient.fetchCoordinates(city, 1, apiKey);

      long duration = System.currentTimeMillis() - startTime;
      log.debug("Successfully retrieved coordinates from GeoClient in {} ms", duration);

    } catch (Exception e) {
      log.error("Failed to communicate with GeoClient for city: '{}'", city, e);
      throw e;
    }

    if(geoResponse.isEmpty()) {
      log.warn("GeoClient returned zero results for city: '{}'. Throwing 404.", city);
      throw new ResourceNotFoundException("City not found: " + city);
    }

    CityCoordinates cityCoordinates = geoResponse.getFirst();
    log.debug("Resolved city: '{}' to Lat: {}, Lon: {}", city, cityCoordinates.lat(), cityCoordinates.lon());

    try {
      long startTime = System.currentTimeMillis();

      SolarEventsResponse response = solarEventsClient.fetchSolarEvents(
              cityCoordinates.lat(),
              cityCoordinates.lon(),
              date
      );

      long duration = System.currentTimeMillis() - startTime;
      log.info("Successfully retrieved solar event data for city: '{}' in {} ms", city, duration);

      return mapToDTO(response, city);

    } catch (Exception e) {
      log.error("Failed to communicate with SolarEventsClient for city: '{}', Lat: {}, Lon: {}",
              city, cityCoordinates.lat(), cityCoordinates.lon(), e);
      throw e;
    }

  }

  private SolarEventsDTO mapToDTO(SolarEventsResponse response, String city) {
    return new SolarEventsDTO(
            city,
            response.results().sunrise(),
            response.results().sunset(),
            response.tzid()
    );
  }

}
