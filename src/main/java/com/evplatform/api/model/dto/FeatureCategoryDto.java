package com.evplatform.api.model.dto;

import com.evplatform.api.model.entity.FeatureCategory;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class FeatureCategoryDto {

  private Integer id;
  private String name;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private List<FeatureDto> features;

  public static FeatureCategoryDto of(FeatureCategory featureCategory) {
    return FeatureCategoryDto.builder()
        .id(featureCategory.getId())
        .name(featureCategory.getName())
        .build();
  }

  public static FeatureCategoryDto ofWithFeatures(FeatureCategory featureCategory) {
    return FeatureCategoryDto.builder()
        .id(featureCategory.getId())
        .name(featureCategory.getName())
        .features(
            featureCategory.getFeatures().stream()
                .map(FeatureDto::of)
                .toList()
        )
        .build();
  }

}

