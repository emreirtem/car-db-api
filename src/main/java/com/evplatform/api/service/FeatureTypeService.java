package com.evplatform.api.service;

import com.evplatform.api.model.entity.FeatureType;
import com.evplatform.api.repository.FeatureTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FeatureTypeService {

  private final FeatureTypeRepository featureTypeRepository;

  public List<FeatureType> findAll() {
    log.debug("Finding all feature types");
    return featureTypeRepository.findAll();
  }

  public Page<FeatureType> findAll(Pageable pageable) {
    log.debug("Finding all feature types with pagination: {}", pageable);
    return featureTypeRepository.findAll(pageable);
  }

  public FeatureType findById(String name) {
    log.debug("Finding feature type by name: {}", name);
    return featureTypeRepository.findById(name)
        .orElseThrow(() -> new EntityNotFoundException("Feature type not found with name: " + name));
  }

  public FeatureType save(FeatureType featureType) {
    log.debug("Saving feature type: {}", featureType.getName());

    if (featureTypeRepository.existsByName(featureType.getName())) {
      throw new IllegalArgumentException("Feature type with name '" + featureType.getName() + "' already exists");
    }

    return featureTypeRepository.save(featureType);
  }

  public FeatureType update(String name, FeatureType typeDetails) {
    log.debug("Updating feature type with name: {}", name);

    FeatureType existingType = findById(name);

    existingType.setValueColumnName(typeDetails.getValueColumnName());

    return featureTypeRepository.save(existingType);
  }

  public void deleteById(String name) {
    log.debug("Deleting feature type with name: {}", name);

    FeatureType type = findById(name);

    if (!type.getFeatures().isEmpty()) {
      throw new IllegalStateException("Cannot delete feature type with existing features");
    }

    featureTypeRepository.deleteById(name);
  }

  public boolean existsByName(String name) {
    return featureTypeRepository.existsByName(name);
  }
}
