package com.evplatform.api.model.dto;

import com.evplatform.api.model.entity.Model;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ModelDto {

  private Integer id;
  private String name;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private BrandDto brand;


  public static ModelDto ofWithBrand(Model model) {
    return ModelDto.builder()
        .id(model.getId())
        .name(model.getName())
        .brand(BrandDto.of(model.getBrand()))
        .build();
  }

  public static ModelDto of(Model model) {
    return ModelDto.builder()
        .id(model.getId())
        .name(model.getName())
        .build();
  }

}

