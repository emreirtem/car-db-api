package com.evplatform.api.model.dto;

import com.evplatform.api.model.entity.Generation;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GenerationDto {
  private Integer id;
  private String name;
  private String faceliftVersion;
  private LocalDate faceliftDate;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private ModelDto model;

  public static GenerationDto of(Generation generation) {
    return GenerationDto.builder()
        .id(generation.getId())
        .name(generation.getName())
        .faceliftVersion(generation.getFaceliftVersion())
        .faceliftDate(generation.getFaceliftDate())
        .build();
  }

  public static GenerationDto ofWithModel(Generation generation) {
    var generationDto = of(generation);
    generationDto.setModel(
        ModelDto.of(generation.getModel())
    );
    return generationDto;
  }

  public static GenerationDto ofWithModelAndBrand(Generation generation) {
    var generationDto = of(generation);
    generationDto.setModel(
        ModelDto.ofWithBrand(generation.getModel())
    );
    return generationDto;
  }

}