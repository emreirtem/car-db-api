package com.evplatform.api.model.dto;

import com.evplatform.api.model.entity.PriceType;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PriceTypeDto {

  private String type;

  public static PriceTypeDto of(PriceType priceType) {
    return PriceTypeDto.builder()
        .type(priceType.getType())
        .build();
  }
}
