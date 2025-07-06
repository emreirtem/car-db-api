package com.evplatform.api.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// TrimLevelFeature Entity
@Entity
@Table(name = "trim_level_features")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrimLevelFeature {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "value_text")
  private String valueText;

  @Column(name = "value_boolean")
  private Boolean valueBoolean;

  @Column(name = "value_numeric")
  private Integer valueNumeric;

  // Many-to-One relationship with TrimLevel
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "trim_level_id", nullable = false)
  private TrimLevel trimLevel;

  // Many-to-One relationship with Feature
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "feature_id", nullable = false)
  private Feature feature;

  // Constructor for convenience
  public TrimLevelFeature(TrimLevel trimLevel, Feature feature) {
    this.trimLevel = trimLevel;
    this.feature = feature;
  }

  public Object getValue() {
    if (valueText != null) return valueText;
    if (valueBoolean != null) return valueBoolean;
    if (valueNumeric != null) return valueNumeric;
    return null;
  }

  // Helper methods to set values based on feature type
  public void setValue(Object value) {
    if (value instanceof String) {
      this.valueText = (String) value;
    } else if (value instanceof Boolean) {
      this.valueBoolean = (Boolean) value;
    } else if (value instanceof Integer) {
      this.valueNumeric = (Integer) value;
    }
  }
}
