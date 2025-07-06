package com.evplatform.api.model.entity;

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

// Feature Entity
@Entity
@Table(name = "features")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Feature {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "name", nullable = false, length = 50)
  private String name;

  @Column(name = "order_in_group")
  private Integer orderInGroup;

  @Column(name = "value_unit", length = 50)
  private String valueUnit;

  // Many-to-One relationship with FeatureType
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "feature_type", nullable = false)
  private FeatureType featureType;

  // Many-to-One relationship with FeatureGroup
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "feature_group_id")
  private FeatureGroup featureGroup;

  // Many-to-One relationship with FeatureCategory
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "feature_category_id", nullable = false)
  private FeatureCategory featureCategory;

  // One-to-Many relationship with TrimLevelFeatures
  @OneToMany(mappedBy = "feature", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @Builder.Default
  private List<TrimLevelFeature> trimLevelFeatures = new ArrayList<>();

  // Constructor for convenience
  public Feature(String name, FeatureType featureType, FeatureCategory featureCategory) {
    this.name = name;
    this.featureType = featureType;
    this.featureCategory = featureCategory;
  }

  // Helper methods
  public void addTrimLevelFeature(TrimLevelFeature trimLevelFeature) {
    trimLevelFeatures.add(trimLevelFeature);
    trimLevelFeature.setFeature(this);
  }

  public void removeTrimLevelFeature(TrimLevelFeature trimLevelFeature) {
    trimLevelFeatures.remove(trimLevelFeature);
    trimLevelFeature.setFeature(null);
  }
}
