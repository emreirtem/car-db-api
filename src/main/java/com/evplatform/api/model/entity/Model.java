package com.evplatform.api.model.entity;

import com.evplatform.api.model.dto.ModelDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "model")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Model {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "name", nullable = false, length = 50)
  private String name;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "brand_id", nullable = false)
  private Brand brand;

  @OneToMany(mappedBy = "model", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @Builder.Default
  private List<Generation> generations = new ArrayList<>();

  @OneToMany(mappedBy = "model", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @Builder.Default
  private List<TrimLevel> trimLevels = new ArrayList<>();

  public Model(String name, Brand brand) {
    this.name = name;
    this.brand = brand;
  }

  public static Model of(ModelDto modelDto) {
    return Model.builder()
        .id(modelDto.getId())
        .name(modelDto.getName())
        .build();
  }

  public static Model ofWithBrand(ModelDto modelDto) {
    var model = of(modelDto);
    model.setBrand(Brand.of(modelDto.getBrand()));
    return model;
  }

  public void addGeneration(Generation generation) {
    generations.add(generation);
    generation.setModel(this);
  }

  public void removeGeneration(Generation generation) {
    generations.remove(generation);
    generation.setModel(null);
  }

  public void addTrimLevel(TrimLevel trimLevel) {
    trimLevels.add(trimLevel);
    trimLevel.setModel(this);
  }

  public void removeTrimLevel(TrimLevel trimLevel) {
    trimLevels.remove(trimLevel);
    trimLevel.setModel(null);
  }


}