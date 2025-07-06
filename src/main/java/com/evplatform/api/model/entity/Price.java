package com.evplatform.api.model.entity;

import com.evplatform.api.model.dto.PriceDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "price")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Price {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "price", nullable = false, precision = 12, scale = 2)
  private BigDecimal price;

  @Column(name = "date")
  private LocalDate date;

  @Column(name = "ref_id")
  private Integer refId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "type", nullable = false)
  private PriceType priceType;

  public Price(BigDecimal price, PriceType priceType) {
    this.price = price;
    this.priceType = priceType;
    this.date = LocalDate.now();
  }

  public static Price of(PriceDto priceDto) {
    return Price.builder()
        .id(priceDto.getId())
        .price(priceDto.getPrice())
        .date(priceDto.getDate())
        .refId(priceDto.getRefId())
        .priceType(PriceType.of(priceDto.getPriceType()))
        .build();
  }
}
