package com.evplatform.api.repository;

import com.evplatform.api.model.entity.Feature;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FeatureRepository extends JpaRepository<Feature, Integer> {

  List<Feature> findByFeatureCategoryId(Integer categoryId);

  List<Feature> findByFeatureGroupId(Integer groupId);

  List<Feature> findByFeatureTypeName(String typeName);

  Optional<Feature> findByNameAndFeatureCategoryId(String name, Integer categoryId);

  @Query("SELECT f FROM Feature f WHERE f.featureGroup.id = :groupId ORDER BY f.orderInGroup")
  List<Feature> findByFeatureGroupIdOrderByOrderInGroup(@Param("groupId") Integer groupId);
}
