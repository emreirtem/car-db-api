package com.evplatform.api.service;

import com.evplatform.api.model.entity.Generation;
import com.evplatform.api.model.entity.Model;
import com.evplatform.api.repository.GenerationRepository;
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
public class GenerationService {

  private final GenerationRepository generationRepository;
  private final ModelService modelService;

  public List<Generation> findAll() {
    log.debug("Finding all generations");
    return generationRepository.findAll();
  }

  public Page<Generation> findAll(Pageable pageable) {
    log.debug("Finding all generations with pagination: {}", pageable);
    return generationRepository.findAll(pageable);
  }

  public Generation findById(Integer id) {
    log.debug("Finding generation by id: {}", id);
    return generationRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Generation not found with id: " + id));
  }

  public List<Generation> findByModelId(Integer modelId) {
    log.debug("Finding generations by model id: {}", modelId);
    return generationRepository.findByModelId(modelId);
  }

  public List<Generation> findByModelIdOrderByFaceliftDate(Integer modelId) {
    log.debug("Finding generations by model id ordered by facelift date: {}", modelId);
    return generationRepository.findByModelIdOrderByFaceliftDateDesc(modelId);
  }

  public Optional<Generation> findByNameAndModelId(String name, Integer modelId) {
    log.debug("Finding generation by name: {} and model id: {}", name, modelId);
    return generationRepository.findByNameAndModelId(name, modelId);
  }

  public Generation save(Generation generation) {
    log.debug("Saving generation: {} for model: {}", generation.getName(), generation.getModel().getName());

    // Validate model exists
    Model model = modelService.findById(generation.getModel().getId());
    generation.setModel(model);

    return generationRepository.save(generation);
  }

  public Generation update(Integer id, Generation generationDetails) {
    log.debug("Updating generation with id: {}", id);

    Generation existingGeneration = findById(id);

    existingGeneration.setName(generationDetails.getName());
    existingGeneration.setFaceliftVersion(generationDetails.getFaceliftVersion());
    existingGeneration.setFaceliftDate(generationDetails.getFaceliftDate());

    // If model is being changed, validate it
    if (generationDetails.getModel() != null &&
        !existingGeneration.getModel().getId().equals(generationDetails.getModel().getId())) {
      Model newModel = modelService.findById(generationDetails.getModel().getId());
      existingGeneration.setModel(newModel);
    }

    return generationRepository.save(existingGeneration);
  }

  public void deleteById(Integer id) {
    log.debug("Deleting generation with id: {}", id);

    Generation generation = findById(id);

    if (!generation.getCars().isEmpty()) {
      throw new IllegalStateException("Cannot delete generation with existing cars");
    }

    generationRepository.deleteById(id);
  }
}
