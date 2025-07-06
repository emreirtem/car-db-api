package com.evplatform.api.service;

import com.evplatform.api.model.entity.Brand;
import com.evplatform.api.repository.BrandRepository;
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
public class BrandService {

  private final BrandRepository brandRepository;

  public List<Brand> findAll() {
    log.debug("Finding all brands");
    return brandRepository.findAll();
  }

  public Page<Brand> findAll(Pageable pageable) {
    log.debug("Finding all brands with pagination: {}", pageable);
    return brandRepository.findAll(pageable);
  }

  public Brand findById(Integer id) {
    log.debug("Finding brand by id: {}", id);
    return brandRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Brand not found with id: " + id));
  }

  public Optional<Brand> findByName(String name) {
    log.debug("Finding brand by name: {}", name);
    return brandRepository.findByName(name);
  }

  public List<Brand> findByNameContaining(String name) {
    log.debug("Finding brands containing name: {}", name);
    return brandRepository.findByNameContainingIgnoreCase(name);
  }

  @Transactional
  public Brand save(Brand brand) {
    log.debug("Saving brand: {}", brand.getName());

    if (brandRepository.existsByNameIgnoreCase(brand.getName())) {
      throw new IllegalArgumentException("Brand with name '" + brand.getName() + "' already exists");
    }

    return brandRepository.save(brand);
  }

  @Transactional
  public Brand update(Integer id, Brand brandDetails) {
    log.debug("Updating brand with id: {}", id);

    Brand existingBrand = brandRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Brand not found with id: " + id));

    // Check if name is being changed and if new name already exists
    if (!existingBrand.getName().equals(brandDetails.getName()) &&
        brandRepository.existsByNameIgnoreCase(brandDetails.getName())) {
      throw new IllegalArgumentException("Brand with name '" + brandDetails.getName() + "' already exists");
    }

    existingBrand.setName(brandDetails.getName());
    return brandRepository.save(existingBrand);
  }

  @Transactional
  public void deleteById(Integer id) {
    log.debug("Deleting brand with id: {}", id);

    Brand brand = brandRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Brand not found with id: " + id));

    if (!brand.getModels().isEmpty()) {
      throw new IllegalStateException("Cannot delete brand with existing models");
    }

    brandRepository.deleteById(id);
  }

  public boolean existsByName(String name) {
    return brandRepository.existsByNameIgnoreCase(name);
  }

}