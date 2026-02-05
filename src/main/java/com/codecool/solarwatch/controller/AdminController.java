package com.codecool.solarwatch.controller;

import com.codecool.solarwatch.model.dto.CityDTO;
import com.codecool.solarwatch.model.dto.CreateCityDTO;
import com.codecool.solarwatch.model.dto.CreateSolarEventDTO;
import com.codecool.solarwatch.model.dto.SolarEventDTO;
import com.codecool.solarwatch.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/admin")
@Slf4j
@RequiredArgsConstructor
public class AdminController {

  private final AdminService adminService;

  @PostMapping("/create/city")
  public CityDTO createCity(@RequestBody CreateCityDTO city) {
    return adminService.createCity(city);
  }

  @PostMapping("/create/solarevent")
  public SolarEventDTO createSolarEvent(@RequestBody CreateSolarEventDTO solarEvent) {
    return adminService.createSolarEvent(solarEvent);
  }

}
