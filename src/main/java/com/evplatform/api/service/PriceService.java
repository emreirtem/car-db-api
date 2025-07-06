package com.evplatform.api.service;

import com.evplatform.api.model.entity.Price;
import com.evplatform.api.model.entity.PriceType;
import com.evplatform.api.repository.PriceRepository;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDate;
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
public class PriceService {

  private final PriceRepository priceRepository;
  private final PriceTypeService priceTypeService;

  public List<Price> findAll() {
    log.debug("Finding all prices");
    return priceRepository.findAll();
  }

  public Page<Price> findAll(Pageable pageable) {
    log.debug("Finding all prices with pagination: {}", pageable);
    return priceRepository.findAll(pageable);
  }

  public Price findById(Integer id) {
    log.debug("Finding price by id: {}", id);
    return priceRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Price not found with id: " + id));
  }

  public List<Price> findByPriceType(String priceType) {
    log.debug("Finding prices by price type: {}", priceType);
    return priceRepository.findByPriceTypeType(priceType);
  }

  public List<Price> findByRefId(Integer refId) {
    log.debug("Finding prices by reference id: {}", refId);
    return priceRepository.findByRefId(refId);
  }

  public List<Price> findByPriceTypeAndRefId(String priceType, Integer refId) {
    log.debug("Finding prices by price type: {} and reference id: {}", priceType, refId);
    return priceRepository.findByPriceTypeTypeAndRefId(priceType, refId);
  }

  public List<Price> findByDateRange(LocalDate startDate, LocalDate endDate) {
    log.debug("Finding prices by date range: {} - {}", startDate, endDate);
    return priceRepository.findByDateRange(startDate, endDate);
  }

  public Optional<Price> findLatestByPriceTypeAndRefId(String priceType, Integer refId) {
    log.debug("Finding latest price by price type: {} and reference id: {}", priceType, refId);
    return priceRepository.findLatestByPriceTypeAndRefId(priceType, refId);
  }

  @Transactional
  public Price save(Price price) {
    log.debug("Saving price: {} for type: {}", price.getPrice(), price.getPriceType().getType());

    // Validate price type exists
    PriceType priceType = priceTypeService.findById(price.getPriceType().getType());
    price.setPriceType(priceType);

    // Validate price value
    if (price.getPrice() == null || price.getPrice() <= 0) {
      throw new IllegalArgumentException("Price value must be positive");
    }

    return priceRepository.save(price);
  }

  @Transactional
  public Price update(Integer id, Price priceDetails) {
    log.debug("Updating price with id: {}", id);

    Price existingPrice = findById(id);

    if (priceDetails.getPrice() == null || priceDetails.getPrice() <= 0) {
      throw new IllegalArgumentException("Price value must be positive");
    }

    existingPrice.setPrice(priceDetails.getPrice());
    existingPrice.setDate(priceDetails.getDate());
    existingPrice.setRefId(priceDetails.getRefId());

    // If price type is being changed, validate it
    if (priceDetails.getPriceType() != null &&
        !existingPrice.getPriceType().getType().equals(priceDetails.getPriceType().getType())) {
      PriceType newPriceType = priceTypeService.findById(priceDetails.getPriceType().getType());
      existingPrice.setPriceType(newPriceType);
    }

    return priceRepository.save(existingPrice);
  }

  @Transactional
  public void deleteById(Integer id) {
    log.debug("Deleting price with id: {}", id);

    Price price = findById(id);
    priceRepository.deleteById(id);
  }
}
