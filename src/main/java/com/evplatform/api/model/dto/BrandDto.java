package com.evplatform.api.model.dto;

import com.evplatform.api.model.entity.Brand;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BrandDto {

  private Integer id;
  private String name;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private List<ModelDto> models;

  public static BrandDto of(Brand brand) {
    return BrandDto.builder()
        .id(brand.getId())
        .name(brand.getName())
        .build();
  }

  public static BrandDto ofWithModels(Brand brand) {
    return BrandDto.builder()
        .id(brand.getId())
        .name(brand.getName())
        .models(
            brand.getModels().stream()
                .map(ModelDto::of)
                .toList())
        .build();
  }
}

