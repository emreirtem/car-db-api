package com.evplatform.api.model.entity;

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

@Entity
@Table(name = "feature_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeatureType {

  @Id
  @Column(name = "name", length = 50)
  private String name;

  @Column(name = "value_column_name", nullable = false, length = 255)
  private String valueColumnName;

  @OneToMany(mappedBy = "featureType", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @Builder.Default
  private List<Feature> features = new ArrayList<>();

  public FeatureType(String name, String valueColumnName) {
    this.name = name;
    this.valueColumnName = valueColumnName;
  }

  public void addFeature(Feature feature) {
    features.add(feature);
    feature.setFeatureType(this);
  }

  public void removeFeature(Feature feature) {
    features.remove(feature);
    feature.setFeatureType(null);
  }
}
