package com.evplatform.api.repository;

import com.evplatform.api.model.entity.FeatureGroup;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeatureGroupRepository extends JpaRepository<FeatureGroup, Integer> {

  Optional<FeatureGroup> findByName(String name);

  List<FeatureGroup> findByMulti(Boolean multi);

  boolean existsByName(String name);
}
