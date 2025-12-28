package com.codecool.solarwatch.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SolarEvent {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Setter(AccessLevel.NONE)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "city_id", nullable = false)
  private City city;

  @Column(nullable = false)
  private LocalDate date;

  private String sunrise;
  private String sunset;
  private String timezone;

}
