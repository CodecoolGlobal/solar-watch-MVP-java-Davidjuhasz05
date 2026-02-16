package com.codecool.solarwatch.client;

import com.codecool.solarwatch.client.response.SolarEventResponse;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.time.LocalDate;

@HttpExchange(url = "https://api.sunrise-sunset.org")
public interface SolarEventClient {

  @GetExchange("/json")
  SolarEventResponse fetchSolarEvent(
          @RequestParam("lat") double lat,
          @RequestParam("lng") double lon,
          @RequestParam("date") LocalDate date
  );

}
