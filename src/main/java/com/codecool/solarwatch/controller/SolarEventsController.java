package com.codecool.solarwatch.controller;

import com.codecool.solarwatch.model.SolarEventsDTO;
import com.codecool.solarwatch.service.SolarEventsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("api")
public class SolarEventsController {

  private final SolarEventsService solarEventsService;

  public SolarEventsController(SolarEventsService solarEventsService) {
    this.solarEventsService = solarEventsService;
  }

  @GetMapping("/solarevents")
  public SolarEventsDTO getSolarEvents(@RequestParam String city, @RequestParam LocalDate date) {
    return solarEventsService.getSolarEvents(city, date);
  }
}
