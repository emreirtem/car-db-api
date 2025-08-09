package com.evplatform.api.service;

import com.evplatform.api.model.entity.Feature;
import com.evplatform.api.model.entity.FeatureCategory;
import com.evplatform.api.model.entity.FeatureGroup;
import com.evplatform.api.model.entity.FeatureType;
import com.evplatform.api.repository.FeatureRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FeatureService {

  private final FeatureRepository featureRepository;
  private final FeatureCategoryService featureCategoryService;
  private final FeatureGroupService featureGroupService;
  private final FeatureTypeService featureTypeService;

  public List<Feature> findAll() {
    log.debug("Finding all features");
    return featureRepository.findAll();
  }

  public Page<Feature> findAll(Pageable pageable) {
    log.debug("Finding all features with pagination: {}", pageable);
    return featureRepository.findAll(pageable);
  }

  public Feature findById(Integer id) {
    log.debug("Finding feature by id: {}", id);
    return featureRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Feature not found with id: " + id));
  }

  public List<Feature> findByFeatureCategoryId(Integer categoryId) {
    log.debug("Finding features by category id: {}", categoryId);
    return featureRepository.findByFeatureCategoryId(categoryId);
  }

  public List<Feature> findByFeatureGroupId(Integer groupId) {
    log.debug("Finding features by group id: {}", groupId);
    return featureRepository.findByFeatureGroupId(groupId);
  }

  public List<Feature> findByFeatureGroupIdOrderByOrder(Integer groupId) {
    log.debug("Finding features by group id ordered by order: {}", groupId);
    return featureRepository.findByFeatureGroupIdOrderByOrderInGroup(groupId);
  }

  public List<Feature> findByFeatureTypeName(String typeName) {
    log.debug("Finding features by type name: {}", typeName);
    return featureRepository.findByFeatureTypeName(typeName);
  }

  public Optional<Feature> findByNameAndFeatureCategoryId(String name, Integer categoryId) {
    log.debug("Finding feature by name: {} and category id: {}", name, categoryId);
    return featureRepository.findByNameAndFeatureCategoryId(name, categoryId);
  }

  public Feature save(Feature feature) {
    log.debug("Saving feature: {}", feature.getName());

    // Validate required relationships
    FeatureCategory category = featureCategoryService.findById(feature.getFeatureCategory().getId());
    FeatureType type = featureTypeService.findById(feature.getFeatureType().getName());

    feature.setFeatureCategory(category);
    feature.setFeatureType(type);

    // Validate optional group relationship
    if (feature.getFeatureGroup() != null && feature.getFeatureGroup().getId() != null) {
      FeatureGroup group = featureGroupService.findById(feature.getFeatureGroup().getId());
      feature.setFeatureGroup(group);
    }

    return featureRepository.save(feature);
  }

  public Feature update(Integer id, Feature featureDetails) {
    log.debug("Updating feature with id: {}", id);

    Feature existingFeature = findById(id);

    existingFeature.setName(featureDetails.getName());
    existingFeature.setOrderInGroup(featureDetails.getOrderInGroup());
    existingFeature.setValueUnit(featureDetails.getValueUnit());

    // Update relationships if changed
    if (featureDetails.getFeatureCategory() != null &&
        !existingFeature.getFeatureCategory().getId().equals(featureDetails.getFeatureCategory().getId())) {
      FeatureCategory newCategory = featureCategoryService.findById(featureDetails.getFeatureCategory().getId());
      existingFeature.setFeatureCategory(newCategory);
    }

    if (featureDetails.getFeatureType() != null &&
        !existingFeature.getFeatureType().getName().equals(featureDetails.getFeatureType().getName())) {
      FeatureType newType = featureTypeService.findById(featureDetails.getFeatureType().getName());
      existingFeature.setFeatureType(newType);
    }

    if (featureDetails.getFeatureGroup() != null && featureDetails.getFeatureGroup().getId() != null) {
      if (existingFeature.getFeatureGroup() == null ||
          !existingFeature.getFeatureGroup().getId().equals(featureDetails.getFeatureGroup().getId())) {
        FeatureGroup newGroup = featureGroupService.findById(featureDetails.getFeatureGroup().getId());
        existingFeature.setFeatureGroup(newGroup);
      }
    } else {
      existingFeature.setFeatureGroup(null);
    }

    return featureRepository.save(existingFeature);
  }

  public void deleteById(Integer id) {
    log.debug("Deleting feature with id: {}", id);

    Feature feature = findById(id);

    if (!feature.getTrimLevelFeatures().isEmpty()) {
      throw new IllegalStateException("Cannot delete feature with existing trim level associations");
    }

    featureRepository.deleteById(id);
  }
}
