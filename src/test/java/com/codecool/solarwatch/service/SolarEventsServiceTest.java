package com.codecool.solarwatch.service;

import com.codecool.solarwatch.client.GeoClient;
import com.codecool.solarwatch.client.SolarEventsClient;
import com.codecool.solarwatch.client.response.GeoResponse;
import com.codecool.solarwatch.client.response.SolarEventsResponse;
import com.codecool.solarwatch.exception.ResourceNotFoundException;
import com.codecool.solarwatch.model.SolarEventsDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SolarEventsServiceTest {

  @Mock
  private GeoClient geoClient;

  @Mock
  private SolarEventsClient solarClient;

  @InjectMocks
  private SolarEventsService solarEventsService;

  @Test
  void getSolarEvents_WhenCityExists_ReturnsDTO() {
    LocalDate date = LocalDate.of(2025, 1, 1);
    GeoResponse mockCity = new GeoResponse("London",10.5, -1.24, "GB", "England");
    SolarEventsResponse mockSolarRes = new SolarEventsResponse(
            new SolarEventsResponse.Results("6:00 AM", "8:00 PM"), "UTC");

    when(geoClient.fetchCityData(eq(mockCity.name()), anyInt(), any()))
            .thenReturn(List.of(mockCity));

    when(solarClient.fetchSolarEvents(mockCity.lat(), mockCity.lon(), date))
            .thenReturn(mockSolarRes);

    SolarEventsDTO result = solarEventsService.getSolarEvents(mockCity.name(), date);

    assertEquals("London", result.city());
    assertEquals("6:00 AM", result.sunrise());
    assertEquals("8:00 PM", result.sunset());
    assertEquals("UTC", result.timezone());
  }

  @Test
  void getSolarEvents_WhenCityDoesNotExist_ThrowsException() {
    String city = "InvalidCity";
    LocalDate date = LocalDate.of(2025, 1, 1);

    when(geoClient.fetchCityData(eq(city), anyInt(), any()))
            .thenReturn(List.of());

    assertThrows(ResourceNotFoundException.class, () -> solarEventsService.getSolarEvents(city, date));
    verifyNoInteractions(solarClient);
  }

}
