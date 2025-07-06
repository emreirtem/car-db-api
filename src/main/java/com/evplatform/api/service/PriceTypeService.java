package com.evplatform.api.service;

import com.evplatform.api.model.entity.PriceType;
import com.evplatform.api.repository.PriceTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
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
public class PriceTypeService {

  private final PriceTypeRepository priceTypeRepository;

  public List<PriceType> findAll() {
    log.debug("Finding all price types");
    return priceTypeRepository.findAll();
  }

  public Page<PriceType> findAll(Pageable pageable) {
    log.debug("Finding all price types with pagination: {}", pageable);
    return priceTypeRepository.findAll(pageable);
  }

  public PriceType findById(String type) {
    log.debug("Finding price type by type: {}", type);
    return priceTypeRepository.findById(type)
        .orElseThrow(() -> new EntityNotFoundException("Price type not found with type: " + type));
  }

  @Transactional
  public PriceType save(PriceType priceType) {
    log.debug("Saving price type: {}", priceType.getType());

    if (priceTypeRepository.existsByType(priceType.getType())) {
      throw new IllegalArgumentException("Price type with type '" + priceType.getType() + "' already exists");
    }

    return priceTypeRepository.save(priceType);
  }

  @Transactional
  public void deleteById(String type) {
    log.debug("Deleting price type with type: {}", type);

    PriceType priceType = findById(type);

    if (!priceType.getPrices().isEmpty()) {
      throw new IllegalStateException("Cannot delete price type with existing prices");
    }

    priceTypeRepository.deleteById(type);
  }

  public boolean existsByType(String type) {
    return priceTypeRepository.existsByType(type);
  }
}
