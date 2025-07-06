package com.evplatform.api.service;

import com.evplatform.api.model.dto.BrandDto;
import com.evplatform.api.model.dto.ModelDto;
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

  public List<BrandDto> findAll() {
    log.debug("Finding all brands");
    return brandRepository.findAll().stream()
        .map(this::toBrandDto)
        .toList();
  }

  public Page<Brand> findAll(Pageable pageable) {
    log.debug("Finding all brands with pagination: {}", pageable);
    return brandRepository.findAll(pageable);
  }

  public BrandDto findById(Integer id) {
    log.debug("Finding brand by id: {}", id);
    var brand = brandRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Brand not found with id: " + id));

    return BrandDto.builder()
        .id(brand.getId())
        .name(brand.getName())
        .models(
            brand.getModels().stream()
                .map(model -> ModelDto.builder()
                    .id(model.getId())
                    .name(model.getName())
                    .build())
                .toList())
        .build();

  }

  public Optional<Brand> findByName(String name) {
    log.debug("Finding brand by name: {}", name);
    return brandRepository.findByName(name);
  }

  public List<BrandDto> findByNameContaining(String name) {
    log.debug("Finding brands containing name: {}", name);
    return brandRepository.findByNameContainingIgnoreCase(name).stream()
        .map(this::toBrandDto)
        .toList();
  }

  @Transactional
  public BrandDto save(Brand brand) {
    log.debug("Saving brand: {}", brand.getName());

    if (brandRepository.existsByNameIgnoreCase(brand.getName())) {
      throw new IllegalArgumentException("Brand with name '" + brand.getName() + "' already exists");
    }

    return toBrandDto(brandRepository.save(brand));
  }

  @Transactional
  public BrandDto update(Integer id, Brand brandDetails) {
    log.debug("Updating brand with id: {}", id);

    Brand existingBrand = brandRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Brand not found with id: " + id));

    // Check if name is being changed and if new name already exists
    if (!existingBrand.getName().equals(brandDetails.getName()) &&
        brandRepository.existsByNameIgnoreCase(brandDetails.getName())) {
      throw new IllegalArgumentException("Brand with name '" + brandDetails.getName() + "' already exists");
    }

    existingBrand.setName(brandDetails.getName());
    return toBrandDto(brandRepository.save(existingBrand));
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

  private BrandDto toBrandDto(Brand brand) {
    return BrandDto.builder()
        .id(brand.getId())
        .name(brand.getName())
        .build();
  }
}