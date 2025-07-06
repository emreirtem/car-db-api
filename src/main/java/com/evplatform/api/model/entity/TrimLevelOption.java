package com.evplatform.api.model.entity;

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

// TrimLevelOption Entity
@Entity
@Table(name = "trim_level_options")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrimLevelOption {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  // Many-to-One relationship with TrimLevel (base trim level)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "trim_level_id", nullable = false)
  private TrimLevel trimLevel;

  // Many-to-One relationship with TrimLevel (option)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "option_id", nullable = false)
  private TrimLevel option;

  // Constructor for convenience
  public TrimLevelOption(TrimLevel trimLevel, TrimLevel option) {
    this.trimLevel = trimLevel;
    this.option = option;
  }
}
