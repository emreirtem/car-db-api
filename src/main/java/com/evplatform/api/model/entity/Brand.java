package com.evplatform.api.model.entity;

import com.evplatform.api.model.dto.BrandDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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

@Entity
@Table(name = "brand")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Brand {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "name", nullable = false, length = 50)
  private String name;

  @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @Builder.Default
  private List<Model> models = new ArrayList<>();

  public static Brand of(BrandDto brandDto) {
    return Brand.builder()
        .id(brandDto.getId())
        .name(brandDto.getName())
        .build();
  }

  public void addModel(Model model) {
    models.add(model);
    model.setBrand(this);
  }

  public void removeModel(Model model) {
    models.remove(model);
    model.setBrand(null);
  }
}