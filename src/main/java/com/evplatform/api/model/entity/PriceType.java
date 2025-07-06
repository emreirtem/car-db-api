package com.evplatform.api.model.entity;

import com.evplatform.api.model.dto.PriceTypeDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

// PriceType Entity
@Entity
@Table(name = "price_type")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PriceType {

  @Id
  @Column(name = "type", nullable = false, length = 50)
  @JdbcTypeCode(SqlTypes.VARCHAR)
  private String type;

  // One-to-Many relationship with Price
  @OneToMany(mappedBy = "priceType", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @Builder.Default
  private List<Price> prices = new ArrayList<>();

  // Constructor for convenience
  public PriceType(String type) {
    this.type = type;
  }

  // Helper methods
  public void addPrice(Price price) {
    prices.add(price);
    price.setPriceType(this);
  }

  public void removePrice(Price price) {
    prices.remove(price);
    price.setPriceType(null);
  }

  public static PriceType of(PriceTypeDto priceTypeDto) {
    return PriceType.builder()
        .type(priceTypeDto.getType())
        .build();
  }
}
