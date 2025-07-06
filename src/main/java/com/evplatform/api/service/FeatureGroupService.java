package com.evplatform.api.service;

import com.evplatform.api.model.entity.FeatureGroup;
import com.evplatform.api.repository.FeatureGroupRepository;
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
public class FeatureGroupService {

  private final FeatureGroupRepository featureGroupRepository;

  public List<FeatureGroup> findAll() {
    log.debug("Finding all feature groups");
    return featureGroupRepository.findAll();
  }

  public Page<FeatureGroup> findAll(Pageable pageable) {
    log.debug("Finding all feature groups with pagination: {}", pageable);
    return featureGroupRepository.findAll(pageable);
  }

  public FeatureGroup findById(Integer id) {
    log.debug("Finding feature group by id: {}", id);
    return featureGroupRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Feature group not found with id: " + id));
  }

  public Optional<FeatureGroup> findByName(String name) {
    log.debug("Finding feature group by name: {}", name);
    return featureGroupRepository.findByName(name);
  }

  public List<FeatureGroup> findByMulti(Boolean multi) {
    log.debug("Finding feature groups by multi: {}", multi);
    return featureGroupRepository.findByMulti(multi);
  }

  @Transactional
  public FeatureGroup save(FeatureGroup featureGroup) {
    log.debug("Saving feature group: {}", featureGroup.getName());

    if (featureGroupRepository.existsByName(featureGroup.getName())) {
      throw new IllegalArgumentException("Feature group with name '" + featureGroup.getName() + "' already exists");
    }

    return featureGroupRepository.save(featureGroup);
  }

  @Transactional
  public FeatureGroup update(Integer id, FeatureGroup groupDetails) {
    log.debug("Updating feature group with id: {}", id);

    FeatureGroup existingGroup = findById(id);

    if (!existingGroup.getName().equals(groupDetails.getName()) &&
        featureGroupRepository.existsByName(groupDetails.getName())) {
      throw new IllegalArgumentException("Feature group with name '" + groupDetails.getName() + "' already exists");
    }

    existingGroup.setName(groupDetails.getName());
    existingGroup.setMulti(groupDetails.getMulti());

    return featureGroupRepository.save(existingGroup);
  }

  @Transactional
  public void deleteById(Integer id) {
    log.debug("Deleting feature group with id: {}", id);

    FeatureGroup group = findById(id);

    if (!group.getFeatures().isEmpty()) {
      throw new IllegalStateException("Cannot delete feature group with existing features");
    }

    featureGroupRepository.deleteById(id);
  }

  public boolean existsByName(String name) {
    return featureGroupRepository.existsByName(name);
  }
}
