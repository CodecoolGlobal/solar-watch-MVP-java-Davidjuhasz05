package com.codecool.solarwatch.service;

import com.codecool.solarwatch.client.GeoClient;
import com.codecool.solarwatch.client.SolarEventsClient;
import com.codecool.solarwatch.client.response.CityCoordinates;
import com.codecool.solarwatch.client.response.SolarEventsResponse;
import com.codecool.solarwatch.exception.ResourceNotFoundException;
import com.codecool.solarwatch.model.SolarEventsDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Service
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
    List<CityCoordinates> geoResponse = geoClient.fetchCoordinates(city, 1, apiKey);

    if(geoResponse.isEmpty()) {
      throw new ResourceNotFoundException("City not found: " + city);
    }

    CityCoordinates cityCoordinates = geoResponse.getFirst();
    SolarEventsResponse response = solarEventsClient.fetchSolarEvents(cityCoordinates.lat(), cityCoordinates.lon(), date);
    return mapToDTO(response, city);

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
