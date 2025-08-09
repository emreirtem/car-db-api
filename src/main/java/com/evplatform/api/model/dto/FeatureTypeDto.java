package com.evplatform.api.model.dto;

import com.evplatform.api.model.entity.Feature;
import com.evplatform.api.model.entity.FeatureType;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FeatureTypeDto {

  private String name;

  private String valueColumnName;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private List<Feature> features = new ArrayList<>();

  public static FeatureTypeDto of(FeatureType featureType) {
    return FeatureTypeDto.builder()
        .name(featureType.getName())
        .valueColumnName(featureType.getValueColumnName())
        .build();
  }

}
