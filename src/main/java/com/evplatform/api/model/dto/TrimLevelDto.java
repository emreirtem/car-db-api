package com.evplatform.api.model.dto;

import com.evplatform.api.model.entity.TrimLevel;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TrimLevelDto {
  private Integer id;
  private String name;
  private String version;
  private Boolean isOption = false;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private ModelDto model;

  public static TrimLevelDto of(TrimLevel trimLevel) {
    return TrimLevelDto.builder()
        .id(trimLevel.getId())
        .name(trimLevel.getName())
        .version(trimLevel.getVersion())
        .isOption(trimLevel.getIsOption())
        .build();
  }

  public static TrimLevelDto ofWithModel(TrimLevel trimLevel) {
    var trimLevelDto = of(trimLevel);
    trimLevelDto.setModel(ModelDto.of(trimLevel.getModel()));
    return trimLevelDto;
  }

  public static TrimLevelDto ofWithModelAndBrand(TrimLevel trimLevel) {
    var trimLevelDto = of(trimLevel);
    trimLevelDto.setModel(ModelDto.ofWithBrand(trimLevel.getModel()));
    return trimLevelDto;
  }

}