package com.codecool.solarwatch.repository;

import com.codecool.solarwatch.model.City;
import com.codecool.solarwatch.model.SolarEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface SolarEventRepository extends JpaRepository<SolarEvent, Long> {
  Optional<SolarEvent> findByCityAndDate(City city, LocalDate date);
  boolean existsByCityNameAndDate(String city, LocalDate date);
}
