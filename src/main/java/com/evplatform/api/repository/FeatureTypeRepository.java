package com.evplatform.api.repository;

import com.evplatform.api.model.entity.FeatureType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeatureTypeRepository extends JpaRepository<FeatureType, String> {

  boolean existsByName(String name);
}
