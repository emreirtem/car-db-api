package com.evplatform.api.model.dto;

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

}

