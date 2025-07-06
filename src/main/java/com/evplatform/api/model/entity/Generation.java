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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "generation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Generation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "facelift_version", length = 20)
  private String faceliftVersion;

  @Column(name = "facelift_date")
  private LocalDate faceliftDate;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "model_id", nullable = false)
  private Model model;

  @OneToMany(mappedBy = "generation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @Builder.Default
  private List<Car> cars = new ArrayList<>();

  public Generation(String name, Model model) {
    this.name = name;
    this.model = model;
  }

  public void addCar(Car car) {
    cars.add(car);
    car.setGeneration(this);
  }

  public void removeCar(Car car) {
    cars.remove(car);
    car.setGeneration(null);
  }
}