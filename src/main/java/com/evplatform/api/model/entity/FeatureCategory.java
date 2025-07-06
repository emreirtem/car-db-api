package com.evplatform.api.model.entity;

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

// FeatureCategory Entity
@Entity
@Table(name = "feature_category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeatureCategory {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "name", nullable = false)
  private String name;

  // One-to-Many relationship with Features
  @OneToMany(mappedBy = "featureCategory", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @Builder.Default
  private List<Feature> features = new ArrayList<>();

  // Constructor for convenience
  public FeatureCategory(String name) {
    this.name = name;
  }

  // Helper methods
  public void addFeature(Feature feature) {
    features.add(feature);
    feature.setFeatureCategory(this);
  }

  public void removeFeature(Feature feature) {
    features.remove(feature);
    feature.setFeatureCategory(null);
  }
}
