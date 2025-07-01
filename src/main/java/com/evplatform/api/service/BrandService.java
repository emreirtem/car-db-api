package com.evplatform.api.service;

import com.evplatform.api.model.dto.BrandDto;
import com.evplatform.api.model.entity.Brand;
import com.evplatform.api.repository.BrandRepository;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BrandService {

  private final BrandRepository brandRepository;

  public List<BrandDto> getAllBrands() {
    return brandRepository.findAll().stream()
        .map(BrandService::mapToBrandDto)
        .toList();
  }

  public BrandDto createBrand(BrandDto brandRequest) {
    if (brandRepository.existsByNameIgnoreCase(brandRequest.getName())) {
      throw new IllegalArgumentException("Brand with name '" + brandRequest.getName() + "' already exists.");
    }
    var brandEntity = mapToBrandEntity(brandRequest);
    var entity = brandRepository.save(brandEntity);
    return mapToBrandDto(entity);
  }

  public BrandDto updateBrand(BrandDto brandRequest) {
    Brand existingBrand = brandRepository.findById(brandRequest.getId())
        .orElseThrow(() -> new NoSuchElementException("Brand not found with id: " + brandRequest.getId()));

    if (brandRepository.existsByNameIgnoreCase(brandRequest.getName())) {
      throw new IllegalArgumentException("Brand with name '" + brandRequest.getName() + "' already exists.");
    }

    existingBrand.setName(brandRequest.getName());

    return mapToBrandDto(brandRepository.save(existingBrand));
  }

  private static Brand mapToBrandEntity(BrandDto brandRequest) {
    return Brand.builder()
        .id(brandRequest.getId())
        .name(brandRequest.getName())
        .build();
  }

  private static BrandDto mapToBrandDto(Brand entity) {
    return BrandDto.builder()
        .id(entity.getId())
        .name(entity.getName())
        .build();
  }


}
