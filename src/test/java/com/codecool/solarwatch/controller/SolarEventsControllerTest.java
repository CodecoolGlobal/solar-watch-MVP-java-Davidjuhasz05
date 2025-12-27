package com.codecool.solarwatch.controller;

import com.codecool.solarwatch.exception.ResourceNotFoundException;
import com.codecool.solarwatch.model.SolarEventsDTO;
import com.codecool.solarwatch.service.SolarEventsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SolarEventsController.class)
class SolarEventsControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private SolarEventsService solarService;

  @Test
  void getSolarEvents_Returns200AndJson() throws Exception {
    String city = "Budapest";
    LocalDate date = LocalDate.of(2025, 1, 1);
    SolarEventsDTO mockResponse = new SolarEventsDTO(city, "HU", "", date, "6:00 AM", "8:00 PM", "CEST");

    given(solarService.getSolarEvents(city, date)).willReturn(mockResponse);

    mockMvc.perform(get("/api/solarevents")
                    .param("city", city)
                    .param("date", "2025-01-01"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.city").value("Budapest"))
            .andExpect(jsonPath("$.sunrise").value("6:00 AM"));
  }

  @Test
  void getSolarEvents_WhenMissingParameter_Returns400() throws Exception {
    mockMvc.perform(get("/api/solarevents")
                    .param("city", "Budapest"))
            .andExpect(status().isBadRequest());
  }

  @Test
  void getSolarEvents_WhenDateIsInvalid_Returns400AndErrorMsg() throws Exception {
    String city = "London";
    String invalidDate = "invalid-date";

    mockMvc.perform(get("/api/solarevents")
                    .param("city", city)
                    .param("date", invalidDate))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.message").value("Invalid Date. Please use YYYY-MM-DD format."));
  }

  @Test
  void getSolarEvents_WhenCityNotFound_Returns404() throws Exception {
    String city = "NotARealCity";
    LocalDate date = LocalDate.of(2025, 1, 1);

    given(solarService.getSolarEvents(city, date))
            .willThrow(new ResourceNotFoundException("City not found: " + city));

    mockMvc.perform(get("/api/solarevents")
                    .param("city", city)
                    .param("date", "2025-01-01"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.message").value("City not found: NotARealCity"));
  }

}
