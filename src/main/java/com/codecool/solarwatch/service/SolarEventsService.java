package com.codecool.solarwatch.service;

import org.springframework.stereotype.Service;

@Service
public class SolarEventsService {

  String SUNRISE_SUNSET_FORMAT_URL = "https://api.sunrise-sunset.org/json?lat=%s&lng=%s&date=%s";
  String GEOCODING_FORMAT_URL = "https://api.openweathermap.org/geo/1.0/direct?q=%s&appid=%s";

}
