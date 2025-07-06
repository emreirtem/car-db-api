package com.evplatform.api.service;

import com.evplatform.api.model.entity.Feature;
import com.evplatform.api.model.entity.TrimLevel;
import com.evplatform.api.model.entity.TrimLevelFeature;
import com.evplatform.api.repository.TrimLevelFeatureRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class TrimLevelFeatureService {

  private final TrimLevelFeatureRepository trimLevelFeatureRepository;
  private final TrimLevelService trimLevelService;
  private final FeatureService featureService;

  public List<TrimLevelFeature> findAll() {
    log.debug("Finding all trim level features");
    return trimLevelFeatureRepository.findAll();
  }

  public Page<TrimLevelFeature> findAll(Pageable pageable) {
    log.debug("Finding all trim level features with pagination: {}", pageable);
    return trimLevelFeatureRepository.findAll(pageable);
  }

  public TrimLevelFeature findById(Integer id) {
    log.debug("Finding trim level feature by id: {}", id);
    return trimLevelFeatureRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Trim level feature not found with id: " + id));
  }

  public List<TrimLevelFeature> findByTrimLevelId(Integer trimLevelId) {
    log.debug("Finding trim level features by trim level id: {}", trimLevelId);
    return trimLevelFeatureRepository.findByTrimLevelId(trimLevelId);
  }

  public List<TrimLevelFeature> findByFeatureId(Integer featureId) {
    log.debug("Finding trim level features by feature id: {}", featureId);
    return trimLevelFeatureRepository.findByFeatureId(featureId);
  }

  public Optional<TrimLevelFeature> findByTrimLevelIdAndFeatureId(Integer trimLevelId, Integer featureId) {
    log.debug("Finding trim level feature by trim level id: {} and feature id: {}", trimLevelId, featureId);
    return trimLevelFeatureRepository.findByTrimLevelIdAndFeatureId(trimLevelId, featureId);
  }

  public List<TrimLevelFeature> findByTrimLevelIdAndFeatureCategoryId(Integer trimLevelId, Integer categoryId) {
    log.debug("Finding trim level features by trim level id: {} and category id: {}", trimLevelId, categoryId);
    return trimLevelFeatureRepository.findByTrimLevelIdAndFeatureCategoryId(trimLevelId, categoryId);
  }

  @Transactional
  public TrimLevelFeature save(TrimLevelFeature trimLevelFeature) {
    log.debug("Saving trim level feature for trim level: {} and feature: {}",
        trimLevelFeature.getTrimLevel().getId(), trimLevelFeature.getFeature().getId());

    // Validate trim level exists
    TrimLevel trimLevel = trimLevelService.findById(trimLevelFeature.getTrimLevel().getId());
    Feature feature = featureService.findById(trimLevelFeature.getFeature().getId());

    trimLevelFeature.setTrimLevel(trimLevel);
    trimLevelFeature.setFeature(feature);

    // Check if association already exists
    Optional<TrimLevelFeature> existing = findByTrimLevelIdAndFeatureId(
        trimLevel.getId(), feature.getId());
    if (existing.isPresent()) {
      throw new IllegalArgumentException("Feature already associated with this trim level");
    }

    // Validate feature value based on feature type
    validateFeatureValue(trimLevelFeature, feature);

    return trimLevelFeatureRepository.save(trimLevelFeature);
  }

  @Transactional
  public TrimLevelFeature update(Integer id, TrimLevelFeature featureDetails) {
    log.debug("Updating trim level feature with id: {}", id);

    TrimLevelFeature existingFeature = findById(id);

    // Update values
    existingFeature.setValueText(featureDetails.getValueText());
    existingFeature.setValueBoolean(featureDetails.getValueBoolean());
    existingFeature.setValueNumeric(featureDetails.getValueNumeric());

    // Validate feature value based on feature type
    validateFeatureValue(existingFeature, existingFeature.getFeature());

    return trimLevelFeatureRepository.save(existingFeature);
  }

  @Transactional
  public void deleteById(Integer id) {
    log.debug("Deleting trim level feature with id: {}", id);

    TrimLevelFeature feature = findById(id);
    trimLevelFeatureRepository.deleteById(id);
  }

  private void validateFeatureValue(TrimLevelFeature trimLevelFeature, Feature feature) {
    String featureType = feature.getFeatureType().getName();
    String valueColumn = feature.getFeatureType().getValueColumnName();

    switch (valueColumn) {
      case "value_text":
        if (trimLevelFeature.getValueText() == null || trimLevelFeature.getValueText().trim().isEmpty()) {
          throw new IllegalArgumentException("Text value is required for feature type: " + featureType);
        }
        break;
      case "value_boolean":
        if (trimLevelFeature.getValueBoolean() == null) {
          throw new IllegalArgumentException("Boolean value is required for feature type: " + featureType);
        }
        break;
      case "value_numeric":
        if (trimLevelFeature.getValueNumeric() == null) {
          throw new IllegalArgumentException("Numeric value is required for feature type: " + featureType);
        }
        break;
      default:
        throw new IllegalArgumentException("Unknown value column: " + valueColumn);
    }
  }
}
