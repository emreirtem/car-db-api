package com.evplatform.api.service;

import com.evplatform.api.model.entity.FeatureCategory;
import com.evplatform.api.repository.FeatureCategoryRepository;
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
public class FeatureCategoryService {

  private final FeatureCategoryRepository featureCategoryRepository;

  public List<FeatureCategory> findAll() {
    log.debug("Finding all feature categories");
    return featureCategoryRepository.findAll();
  }

  public Page<FeatureCategory> findAll(Pageable pageable) {
    log.debug("Finding all feature categories with pagination: {}", pageable);
    return featureCategoryRepository.findAll(pageable);
  }

  public FeatureCategory findById(Integer id) {
    log.debug("Finding feature category by id: {}", id);
    return featureCategoryRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Feature category not found with id: " + id));
  }

  public Optional<FeatureCategory> findByName(String name) {
    log.debug("Finding feature category by name: {}", name);
    return featureCategoryRepository.findByName(name);
  }

  public FeatureCategory save(FeatureCategory featureCategory) {
    log.debug("Saving feature category: {}", featureCategory.getName());

    if (featureCategoryRepository.existsByName(featureCategory.getName())) {
      throw new IllegalArgumentException("Feature category with name '" + featureCategory.getName() + "' already exists");
    }

    return featureCategoryRepository.save(featureCategory);
  }

  public FeatureCategory update(Integer id, FeatureCategory categoryDetails) {
    log.debug("Updating feature category with id: {}", id);

    FeatureCategory existingCategory = findById(id);

    if (!existingCategory.getName().equals(categoryDetails.getName()) &&
        featureCategoryRepository.existsByName(categoryDetails.getName())) {
      throw new IllegalArgumentException("Feature category with name '" + categoryDetails.getName() + "' already exists");
    }

    existingCategory.setName(categoryDetails.getName());
    return featureCategoryRepository.save(existingCategory);
  }

  public void deleteById(Integer id) {
    log.debug("Deleting feature category with id: {}", id);

    FeatureCategory category = findById(id);

    if (!category.getFeatures().isEmpty()) {
      throw new IllegalStateException("Cannot delete feature category with existing features");
    }

    featureCategoryRepository.deleteById(id);
  }

  public boolean existsByName(String name) {
    return featureCategoryRepository.existsByName(name);
  }
}
