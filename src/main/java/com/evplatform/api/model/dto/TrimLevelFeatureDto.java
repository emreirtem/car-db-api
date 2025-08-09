package com.evplatform.api.model.dto;

import com.evplatform.api.model.entity.TrimLevelFeature;
import lombok.Builder;
import lombok.Data;

// TrimLevelFeature Entity
@Data
@Builder
public class TrimLevelFeatureDto {

  private Integer id;

  private String valueText;

  private Boolean valueBoolean;

  private Integer valueNumeric;

  private TrimLevelDto trimLevel;

  private FeatureDto feature;

  public static TrimLevelFeatureDto of(TrimLevelFeature trimLevelFeature) {
    return TrimLevelFeatureDto.builder()
        .id(trimLevelFeature.getId())
        .valueText(trimLevelFeature.getValueText())
        .valueBoolean(trimLevelFeature.getValueBoolean())
        .valueNumeric(trimLevelFeature.getValueNumeric())
        .trimLevel(TrimLevelDto.of(trimLevelFeature.getTrimLevel()))
        .feature(FeatureDto.of(trimLevelFeature.getFeature()))
        .build();
  }

}
