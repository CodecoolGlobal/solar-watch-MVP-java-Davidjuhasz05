package com.codecool.solarwatch.controller;

import com.codecool.solarwatch.model.dto.CityDTO;
import com.codecool.solarwatch.model.dto.CreateCityDTO;
import com.codecool.solarwatch.model.dto.CreateSolarEventDTO;
import com.codecool.solarwatch.model.dto.SolarEventDTO;
import com.codecool.solarwatch.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/admin")
@Slf4j
@RequiredArgsConstructor
public class AdminController {

  private final AdminService adminService;

  @PostMapping("/create/city")
  public ResponseEntity<CityDTO> createCity(@RequestBody CreateCityDTO city) {
    CityDTO cityDTO = adminService.createCity(city);
    return ResponseEntity.status(HttpStatus.CREATED).body(cityDTO);
  }

  @PostMapping("/create/solarevent")
  public ResponseEntity<SolarEventDTO> createSolarEvent(@RequestBody CreateSolarEventDTO solarEvent) {
    SolarEventDTO solarEventDTO = adminService.createSolarEvent(solarEvent);
    return ResponseEntity.status(HttpStatus.CREATED).body(solarEventDTO);
  }

}
