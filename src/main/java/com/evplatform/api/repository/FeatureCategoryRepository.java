package com.evplatform.api.repository;

import com.evplatform.api.model.entity.FeatureCategory;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeatureCategoryRepository extends JpaRepository<FeatureCategory, Integer> {

  Optional<FeatureCategory> findByName(String name);

  boolean existsByName(String name);
}
