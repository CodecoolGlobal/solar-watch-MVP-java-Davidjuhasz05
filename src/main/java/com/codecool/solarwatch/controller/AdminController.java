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
  public ResponseEntity<CityDTO> createCity(@RequestBody CreateCityDTO cityDTO) {
    CityDTO city = adminService.createCity(cityDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(city);
  }

  @PostMapping("/create/solarevent")
  public ResponseEntity<SolarEventDTO> createSolarEvent(@RequestBody CreateSolarEventDTO solarEventDTO) {
    SolarEventDTO solarEvent = adminService.createSolarEvent(solarEventDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(solarEvent);
  }

  @PutMapping("/update/city/{id}")
  public ResponseEntity<CityDTO> updateCity(@RequestBody CityDTO cityDTO, @PathVariable Long id) {
    CityDTO city = adminService.updateCity(cityDTO, id);
    return ResponseEntity.status(HttpStatus.OK).body(city);
  }

  @PutMapping("/update/solarevent/{id}")
  public ResponseEntity<SolarEventDTO> updateSolarEvent(@RequestBody SolarEventDTO solarEventDTO, @PathVariable Long id) {
    SolarEventDTO solarEvent = adminService.updateSolarEvent(solarEventDTO, id);
    return ResponseEntity.status(HttpStatus.OK).body(solarEvent);
  }

}
