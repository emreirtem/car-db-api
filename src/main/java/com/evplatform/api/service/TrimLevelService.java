package com.evplatform.api.service;

import com.evplatform.api.model.entity.Model;
import com.evplatform.api.model.entity.TrimLevel;
import com.evplatform.api.repository.TrimLevelRepository;
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
public class TrimLevelService {

  private final TrimLevelRepository trimLevelRepository;
  private final ModelService modelService;

  public List<TrimLevel> findAll() {
    log.debug("Finding all trim levels");
    return trimLevelRepository.findAll();
  }

  public Page<TrimLevel> findAll(Pageable pageable) {
    log.debug("Finding all trim levels with pagination: {}", pageable);
    return trimLevelRepository.findAll(pageable);
  }

  public TrimLevel findById(Integer id) {
    log.debug("Finding trim level by id: {}", id);
    return trimLevelRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Trim level not found with id: " + id));
  }

  public List<TrimLevel> findByModelId(Integer modelId) {
    log.debug("Finding trim levels by model id: {}", modelId);
    return trimLevelRepository.findByModelId(modelId);
  }

  public List<TrimLevel> findBaseTrimLevelsByModelId(Integer modelId) {
    log.debug("Finding base trim levels by model id: {}", modelId);
    return trimLevelRepository.findBaseTrimLevelsByModelId(modelId);
  }

  public List<TrimLevel> findByIsOption(Boolean isOption) {
    log.debug("Finding trim levels by isOption: {}", isOption);
    return trimLevelRepository.findByIsOption(isOption);
  }

  public Optional<TrimLevel> findByNameAndModelId(String name, Integer modelId) {
    log.debug("Finding trim level by name: {} and model id: {}", name, modelId);
    return trimLevelRepository.findByNameAndModelId(name, modelId);
  }

  @Transactional
  public TrimLevel save(TrimLevel trimLevel) {
    log.debug("Saving trim level: {} for model: {}", trimLevel.getName(), trimLevel.getModel().getName());

    // Validate model exists
    trimLevel.setModel(modelService.findById(trimLevel.getModel().getId()));

    return trimLevelRepository.save(trimLevel);
  }

  @Transactional
  public TrimLevel update(Integer id, TrimLevel trimLevelDetails) {
    log.debug("Updating trim level with id: {}", id);

    TrimLevel existingTrimLevel = findById(id);

    existingTrimLevel.setName(trimLevelDetails.getName());
    existingTrimLevel.setVersion(trimLevelDetails.getVersion());
    existingTrimLevel.setIsOption(trimLevelDetails.getIsOption());

    // If model is being changed, validate it
    if (trimLevelDetails.getModel() != null &&
        !existingTrimLevel.getModel().getId().equals(trimLevelDetails.getModel().getId())) {
      Model newModel = modelService.findById(trimLevelDetails.getModel().getId());
      existingTrimLevel.setModel(newModel);
    }

    return trimLevelRepository.save(existingTrimLevel);
  }

  @Transactional
  public void deleteById(Integer id) {
    log.debug("Deleting trim level with id: {}", id);

    TrimLevel trimLevel = findById(id);

    if (!trimLevel.getCars().isEmpty()) {
      throw new IllegalStateException("Cannot delete trim level with existing cars");
    }

    trimLevelRepository.deleteById(id);
  }
}
