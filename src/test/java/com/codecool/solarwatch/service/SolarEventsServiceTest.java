package com.codecool.solarwatch.service;

import com.codecool.solarwatch.client.GeoClient;
import com.codecool.solarwatch.client.SolarEventsClient;
import com.codecool.solarwatch.client.response.GeoResponse;
import com.codecool.solarwatch.client.response.SolarEventsResponse;
import com.codecool.solarwatch.exception.ResourceNotFoundException;
import com.codecool.solarwatch.model.City;
import com.codecool.solarwatch.model.SolarEvent;
import com.codecool.solarwatch.model.SolarEventsDTO;
import com.codecool.solarwatch.repository.CityRepository;
import com.codecool.solarwatch.repository.SolarEventRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SolarEventsServiceTest {

  @Mock private GeoClient geoClient;
  @Mock private SolarEventsClient solarClient;
  @Mock private CityRepository cityRepository;
  @Mock private SolarEventRepository solarEventRepository;

  @InjectMocks
  private SolarEventsService service;

  private final LocalDate DATE = LocalDate.of(2023, 10, 5);
  private final String CITY_NAME = "London";
  private final String CITY_NAME_LOWER = "london";

  @Test
  @DisplayName("Should return cached event when City and Event exist")
  void getSolarEvents_FullCacheHit() {
    City city = new City(1L, CITY_NAME, 51.5, -0.1, "GB", "England", null);
    SolarEvent event = new SolarEvent(10L, city, DATE, "6:00 AM", "6:00 PM", "UTC");

    when(cityRepository.findByNameIgnoreCase(CITY_NAME)).thenReturn(Optional.of(city));
    when(solarEventRepository.findByCityAndDate(city, DATE)).thenReturn(Optional.of(event));

    SolarEventsDTO result = service.getSolarEvents(CITY_NAME, DATE);

    assertNotNull(result);
    assertEquals(CITY_NAME, result.city());
    assertEquals("6:00 AM", result.sunrise());

    verify(cityRepository).findByNameIgnoreCase(CITY_NAME);
    verifyNoInteractions(geoClient, solarClient);
  }

  @Test
  @DisplayName("Should return cached event even when input case differs from DB case")
  void getSolarEvents_CaseInsensitiveCacheHit() {
    City city = new City(1L, CITY_NAME, 51.5, -0.1, "GB", "England", null);
    SolarEvent event = new SolarEvent(10L, city, DATE, "7:00 AM", "7:00 PM", "UTC");

    when(cityRepository.findByNameIgnoreCase(CITY_NAME_LOWER)).thenReturn(Optional.of(city));
    when(solarEventRepository.findByCityAndDate(city, DATE)).thenReturn(Optional.of(event));

    SolarEventsDTO result = service.getSolarEvents(CITY_NAME_LOWER, DATE);

    assertEquals(CITY_NAME, result.city());

    verify(cityRepository).findByNameIgnoreCase(CITY_NAME_LOWER);
    verifyNoInteractions(geoClient, solarClient);
  }

  @Test
  @DisplayName("Should fetch City and Event from API when cache misses completely")
  void getSolarEvents_FullCacheMiss() {
    GeoResponse geoResponse = new GeoResponse(CITY_NAME, 51.5, -0.1, "GB", "England");
    SolarEventsResponse solarResponse = new SolarEventsResponse(
            new SolarEventsResponse.Results("6:00 AM", "8:00 PM"), "UTC");

    when(cityRepository.findByNameIgnoreCase(CITY_NAME)).thenReturn(Optional.empty());
    when(geoClient.fetchCityData(eq(CITY_NAME), eq(1), any())).thenReturn(List.of(geoResponse));
    when(cityRepository.save(any(City.class))).thenAnswer(i -> {
      City c = (City) i.getArguments()[0];
      c.setId(1L);
      return c;
    });

    when(solarClient.fetchSolarEvents(51.5, -0.1, DATE)).thenReturn(solarResponse);
    when(solarEventRepository.save(any(SolarEvent.class))).thenAnswer(i -> i.getArguments()[0]);

    SolarEventsDTO result = service.getSolarEvents(CITY_NAME, DATE);

    assertEquals(CITY_NAME, result.city());
    verify(cityRepository, times(2)).findByNameIgnoreCase(CITY_NAME);
    verify(cityRepository).save(any(City.class));
  }

  @Test
  @DisplayName("Should fetch Event from API when City exists but Event does not")
  void getSolarEvents_CityHit_EventMiss() {
    City city = new City(1L, CITY_NAME, 51.5, -0.1, "GB", "England", null);
    SolarEventsResponse apiResponse = new SolarEventsResponse(
            new SolarEventsResponse.Results("7:00 AM", "7:00 PM"), "UTC");

    when(cityRepository.findByNameIgnoreCase(CITY_NAME)).thenReturn(Optional.of(city));
    when(solarEventRepository.findByCityAndDate(city, DATE)).thenReturn(Optional.empty());
    when(solarClient.fetchSolarEvents(city.getLatitude(), city.getLongitude(), DATE)).thenReturn(apiResponse);
    when(solarEventRepository.save(any(SolarEvent.class))).thenAnswer(i -> i.getArguments()[0]);

    SolarEventsDTO result = service.getSolarEvents(CITY_NAME, DATE);

    assertEquals("7:00 AM", result.sunrise());
    verify(solarEventRepository).save(any(SolarEvent.class));
  }

  @Test
  @DisplayName("Should throw ResourceNotFoundException when City not found in DB or API")
  void getSolarEvents_CityNotFoundAnywhere() {
    String unknownCity = "NotARealCity";
    when(cityRepository.findByNameIgnoreCase(unknownCity)).thenReturn(Optional.empty());
    when(geoClient.fetchCityData(eq(unknownCity), anyInt(), any())).thenReturn(List.of());

    assertThrows(ResourceNotFoundException.class, () -> service.getSolarEvents(unknownCity, DATE));
  }

}
