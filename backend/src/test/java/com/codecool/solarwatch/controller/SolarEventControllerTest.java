package com.codecool.solarwatch.controller;

import com.codecool.solarwatch.exception.ResourceNotFoundException;
import com.codecool.solarwatch.model.dto.SolarEventDTO;
import com.codecool.solarwatch.service.SolarEventService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SolarEventController.class)
class SolarEventControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private SolarEventService solarService;

  @Test
  @DisplayName("GET /api/solarevents - Success")
  void getSolarEvent_Success() throws Exception {
    String city = "Budapest";
    String dateStr = "2023-10-05";
    LocalDate date = LocalDate.parse(dateStr);
    SolarEventDTO responseDto = new SolarEventDTO(city, "HU", "Pest", date, "06:00", "18:00", "CET");

    given(solarService.getSolarEvent(city, date)).willReturn(responseDto);

    mockMvc.perform(get("/api/solarevents")
                    .param("city", city)
                    .param("date", dateStr))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.city").value(city))
            .andExpect(jsonPath("$.country").value("HU"))
            .andExpect(jsonPath("$.sunrise").value("06:00"))
            .andExpect(jsonPath("$.sunset").value("18:00"));

    verify(solarService).getSolarEvent(city, date);
  }

  @Test
  @DisplayName("GET /api/solarevents - Missing 'city' parameter should return 400")
  void getSolarEvent_MissingCityParam() throws Exception {
    mockMvc.perform(get("/api/solarevents")
                    .param("date", "2023-10-05"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.message").value("Missing parameter: city"));
  }

  @Test
  @DisplayName("GET /api/solarevents - Invalid Date Format should return 400 with custom message")
  void getSolarEvent_InvalidDateFormat() throws Exception {
    mockMvc.perform(get("/api/solarevents")
                    .param("city", "London")
                    .param("date", "05-10-2023"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("Invalid Date. Please use YYYY-MM-DD format."));
  }

  @Test
  @DisplayName("GET /api/solarevents - City Not Found should return 404")
  void getSolarEvent_CityNotFound() throws Exception {
    String city = "Narnia";
    LocalDate date = LocalDate.of(2023, 10, 5);

    given(solarService.getSolarEvent(city, date))
            .willThrow(new ResourceNotFoundException(city));

    mockMvc.perform(get("/api/solarevents")
                    .param("city", city)
                    .param("date", "2023-10-05"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.status").value(404))
            .andExpect(jsonPath("$.message").value("City not found: 'Narnia'"));
  }

  @Test
  @DisplayName("GET /api/solarevents - Internal Server Error should return 500")
  void getSolarEvent_InternalServerError() throws Exception {
    String city = "Paris";
    LocalDate date = LocalDate.of(2023, 10, 5);

    given(solarService.getSolarEvent(city, date))
            .willThrow(new RuntimeException("Database connection failed"));

    mockMvc.perform(get("/api/solarevents")
                    .param("city", city)
                    .param("date", "2023-10-05"))
            .andExpect(status().isInternalServerError())
            .andExpect(jsonPath("$.status").value(500))
            .andExpect(jsonPath("$.message").value("Something went wrong. Please try again."));
  }

}
