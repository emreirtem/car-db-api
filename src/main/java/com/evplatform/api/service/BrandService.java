package com.evplatform.api.service;

import com.evplatform.api.model.entity.Brand;
import com.evplatform.api.repository.BrandRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BrandService {

  private final BrandRepository brandRepository;

  public List<String> getBrands() {
    return brandRepository.findAll()
        .stream()
        .map(Brand::getName)
        .toList();
  }
}
