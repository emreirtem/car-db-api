package com.evplatform.api.model.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "trim_level")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrimLevel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "name", nullable = false, length = 50)
  private String name;

  @Column(name = "version", length = 50)
  private String version;

  @Column(name = "is_option", nullable = false)
  @Builder.Default
  private Boolean isOption = false;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "model_id", nullable = false)
  private Model model;

  @OneToMany(mappedBy = "trimLevel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @Builder.Default
  private List<Car> cars = new ArrayList<>();

  @OneToMany(mappedBy = "trimLevel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @Builder.Default
  private List<TrimLevelFeature> trimLevelFeatures = new ArrayList<>();

  @OneToMany(mappedBy = "trimLevel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @Builder.Default
  private List<TrimLevelOption> trimLevelOptions = new ArrayList<>();

  @OneToMany(mappedBy = "option", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @Builder.Default
  private List<TrimLevelOption> optionUsages = new ArrayList<>();

  public TrimLevel(String name, Model model) {
    this.name = name;
    this.model = model;
  }

  public void addCar(Car car) {
    cars.add(car);
    car.setTrimLevel(this);
  }

  public void removeCar(Car car) {
    cars.remove(car);
    car.setTrimLevel(null);
  }

  public void addFeature(TrimLevelFeature feature) {
    trimLevelFeatures.add(feature);
    feature.setTrimLevel(this);
  }

  public void removeFeature(TrimLevelFeature feature) {
    trimLevelFeatures.remove(feature);
    feature.setTrimLevel(null);
  }
}