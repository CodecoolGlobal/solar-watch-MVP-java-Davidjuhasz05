package com.codecool.solarwatch.configuration;

import com.codecool.solarwatch.client.GeoClient;
import com.codecool.solarwatch.client.SolarEventClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.service.registry.ImportHttpServices;

@Configuration(proxyBeanMethods = false)
@ImportHttpServices({ SolarEventClient.class, GeoClient.class})
public class ClientConfig {
}
