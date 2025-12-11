package com.codecool.solarwatch.client;

import com.codecool.solarwatch.client.response.CityCoordinates;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;

@HttpExchange(url = "https://api.openweathermap.org")
public interface GeoClient {

  @GetExchange("/geo/1.0/direct")
  List<CityCoordinates> fetchCoordinates(
          @RequestParam("q") String city,
          @RequestParam("limit") int limit,
          @RequestParam("appid") String apiKey
  );

}
