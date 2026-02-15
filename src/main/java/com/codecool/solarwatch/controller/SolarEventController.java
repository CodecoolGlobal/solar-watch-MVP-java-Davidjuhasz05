package com.codecool.solarwatch.controller;

import com.codecool.solarwatch.model.dto.SolarEventDTO;
import com.codecool.solarwatch.service.SolarEventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class SolarEventController {

  private final SolarEventService solarEventService;

  @GetMapping("/solar-event")
  public SolarEventDTO getSolarEvent(@RequestParam String city, @RequestParam LocalDate date) {
    log.info("Incoming request to fetch sunrise/sunset details for city: '{}', date: {}", city, date);
    long startTime = System.currentTimeMillis();

    SolarEventDTO response = solarEventService.getSolarEvent(city, date);

    long duration = System.currentTimeMillis() - startTime;
    log.info("Successfully fetched sunrise/sunset details for city: '{}', date: {}, in {} ms", response.city(), date, duration);

    return response;
  }

}
