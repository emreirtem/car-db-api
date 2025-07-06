package com.evplatform.api.model.dto;

import com.evplatform.api.model.entity.Car;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CarDto {
  private Integer id;
  private Integer year;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private ModelDto model;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private GenerationDto generation;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private TrimLevelDto trimLevel;

  public static CarDto of(Car car) {
    return CarDto.builder()
        .id(car.getId())
        .year(car.getYear())
        .build();
  }

  public static CarDto ofWithAllInfo(Car car) {
    var carDto = of(car);
    carDto.setModel(ModelDto.ofWithBrand(car.getModel()));
    carDto.setGeneration(GenerationDto.of(car.getGeneration()));
    carDto.setTrimLevel(TrimLevelDto.of(car.getTrimLevel()));
    return carDto;
  }

  public static CarDto ofWithModelAndTrimLevel(Car car) {
    var carDto = of(car);
    carDto.setModel(ModelDto.ofWithBrand(car.getModel()));
    carDto.setTrimLevel(TrimLevelDto.of(car.getTrimLevel()));
    return carDto;
  }


}
