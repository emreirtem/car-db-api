package com.evplatform.api.model.dto;

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
}

