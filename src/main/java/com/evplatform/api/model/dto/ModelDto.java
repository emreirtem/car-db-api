package com.evplatform.api.model.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ModelDto {
  private Long id;
  private BrandDto brand;
  private String name;

}

