package com.evplatform.api.model.dto;

import com.evplatform.api.model.entity.Price;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PriceDto {

  private Integer id;
  private BigDecimal price;
  private LocalDate date;
  private Integer refId;
  private PriceTypeDto priceType;

  public static PriceDto of(Price price) {
    return PriceDto.builder()
        .id(price.getId())
        .price(price.getPrice())
        .date(price.getDate())
        .refId(price.getRefId())
        .priceType(PriceTypeDto.of(price.getPriceType()))
        .build();
  }
}
