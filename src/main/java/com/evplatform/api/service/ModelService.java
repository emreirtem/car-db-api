package com.evplatform.api.service;

import com.evplatform.api.model.entity.Brand;
import com.evplatform.api.model.entity.Model;
import com.evplatform.api.repository.ModelRepository;
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
public class ModelService {

  private final ModelRepository modelRepository;
  private final BrandService brandService;

  public List<Model> findAll() {
    log.debug("Finding all models");
    return modelRepository.findAll();
  }

  public Page<Model> findAll(Pageable pageable) {
    log.debug("Finding all models with pagination: {}", pageable);
    return modelRepository.findAll(pageable);
  }

  public Model findById(Integer id) {
    log.debug("Finding model by id: {}", id);
    return modelRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Model not found with id: " + id));
  }

  public List<Model> findByBrandId(Integer brandId) {
    log.debug("Finding models by brand id: {}", brandId);
    return modelRepository.findByBrandId(brandId);
  }

  public Optional<Model> findByNameAndBrandId(String name, Integer brandId) {
    log.debug("Finding model by name: {} and brand id: {}", name, brandId);
    return modelRepository.findByNameAndBrandId(name, brandId);
  }

  public List<Model> findByNameContaining(String name) {
    log.debug("Finding models containing name: {}", name);
    return modelRepository.findByNameContainingIgnoreCase(name);
  }

  @Transactional
  public Model save(Model model) {
    log.debug("Saving model: {} for brand: {}", model.getName(), model.getBrand().getName());

    if (modelRepository.existsByNameAndBrandId(model.getName(), model.getBrand().getId())) {
      throw new IllegalArgumentException("Model with name '" + model.getName() +
          "' already exists for this brand");
    }

    // Validate brand exists
    Brand brand = brandService.findById(model.getBrand().getId());
    model.setBrand(brand);

    return modelRepository.save(model);
  }

  @Transactional
  public Model update(Integer id, Model modelDetails) {
    log.debug("Updating model with id: {}", id);

    Model existingModel = findById(id);

    // Check if name is being changed and if new name already exists for the brand
    if (!existingModel.getName().equals(modelDetails.getName()) &&
        modelRepository.existsByNameAndBrandId(modelDetails.getName(), existingModel.getBrand().getId())) {
      throw new IllegalArgumentException("Model with name '" + modelDetails.getName() +
          "' already exists for this brand");
    }

    existingModel.setName(modelDetails.getName());

    // If brand is being changed, validate it
    if (modelDetails.getBrand() != null &&
        !existingModel.getBrand().getId().equals(modelDetails.getBrand().getId())) {
      Brand newBrand = brandService.findById(modelDetails.getBrand().getId());
      existingModel.setBrand(newBrand);
    }

    return modelRepository.save(existingModel);
  }

  @Transactional
  public void deleteById(Integer id) {
    log.debug("Deleting model with id: {}", id);

    Model model = findById(id);

    if (!model.getGenerations().isEmpty()) {
      throw new IllegalStateException("Cannot delete model with existing generations");
    }

    if (!model.getTrimLevels().isEmpty()) {
      throw new IllegalStateException("Cannot delete model with existing trim levels");
    }

    modelRepository.deleteById(id);
  }

  public boolean existsByNameAndBrandId(String name, Integer brandId) {
    return modelRepository.existsByNameAndBrandId(name, brandId);
  }
}
