package com.codecool.solarwatch.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class City {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Setter(AccessLevel.NONE)
  private Long id;

  @Column(nullable = false,  unique = true)
  private String name;

  private double latitude;
  private double longitude;
  private String country;
  private String state;

  @OneToMany(mappedBy = "city", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<SolarEvent> solarEvents = new ArrayList<>();

}
