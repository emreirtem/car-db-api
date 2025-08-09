package com.evplatform.api.model.dto;

import com.evplatform.api.model.entity.Feature;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Data;

// Feature Entity
@Data
@Builder
public class FeatureDto {

  private Integer id;

  private String name;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Integer orderInGroup;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String valueUnit;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private FeatureTypeDto featureType;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private FeatureGroupDto featureGroup;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private FeatureCategoryDto featureCategory;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private List<TrimLevelFeatureDto> trimLevelFeatures = new ArrayList<>();

  public static FeatureDto of(Feature feature) {
    return FeatureDto.builder()
        .id(feature.getId())
        .name(feature.getName())
        .orderInGroup(feature.getOrderInGroup())
        .valueUnit(feature.getValueUnit())
        .featureType(FeatureTypeDto.of(feature.getFeatureType()))
        .featureGroup(FeatureGroupDto.of(feature.getFeatureGroup()))
        .featureCategory(FeatureCategoryDto.of(feature.getFeatureCategory()))
        .build();
  }
}
