package com.evplatform.api.model.entity;

import com.evplatform.api.model.dto.CarDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cars")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Car {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "year", nullable = false)
  private Integer year;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "model_id", nullable = false)
  private Model model;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "generation_id", nullable = false)
  private Generation generation;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "trim_level_id", nullable = false)
  private TrimLevel trimLevel;

  public Car(Integer year, Model model, Generation generation, TrimLevel trimLevel) {
    this.year = year;
    this.model = model;
    this.generation = generation;
    this.trimLevel = trimLevel;
  }

  public static Car of(CarDto carDto) {
    return Car.builder()
        .id(carDto.getId())
        .year(carDto.getYear())
        .model(Model.of(carDto.getModel()))
        .generation(Generation.of(carDto.getGeneration()))
        .trimLevel(TrimLevel.of(carDto.getTrimLevel()))
        .build();
  }

}