package com.evplatform.api.repository;

import com.evplatform.api.model.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {
  // gerekirse custom query de ekleyebilirsin
}