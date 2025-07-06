package com.evplatform.api.repository;

import com.evplatform.api.model.entity.TrimLevelFeature;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TrimLevelFeatureRepository extends JpaRepository<TrimLevelFeature, Integer> {

  List<TrimLevelFeature> findByTrimLevelId(Integer trimLevelId);

  List<TrimLevelFeature> findByFeatureId(Integer featureId);

  Optional<TrimLevelFeature> findByTrimLevelIdAndFeatureId(Integer trimLevelId, Integer featureId);

  @Query("SELECT tlf FROM TrimLevelFeature tlf WHERE tlf.trimLevel.id = :trimLevelId AND tlf.feature.featureCategory.id = :categoryId")
  List<TrimLevelFeature> findByTrimLevelIdAndFeatureCategoryId(@Param("trimLevelId") Integer trimLevelId, @Param("categoryId") Integer categoryId);
}
