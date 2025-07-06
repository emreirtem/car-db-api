package com.evplatform.api.repository;

import com.evplatform.api.model.entity.PriceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceTypeRepository extends JpaRepository<PriceType, String> {

  boolean existsByType(String type);
}
