package com.evplatform.api.model.dto;

import com.evplatform.api.model.entity.FeatureGroup;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Data;

// FeatureGroup Entity
@Data
@Builder
public class FeatureGroupDto {

  private Integer id;

  private String name;

  private Boolean multi;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private List<FeatureDto> features = new ArrayList<>();

  public static FeatureGroupDto of(FeatureGroup featureGroup) {
    if (featureGroup != null) {
      return FeatureGroupDto.builder()
          .id(featureGroup.getId())
          .name(featureGroup.getName())
          .multi(featureGroup.getMulti())
          .build();

    }
    return null;
  }

}
