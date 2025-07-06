package com.evplatform.api.repository;

import com.evplatform.api.model.entity.TrimLevel;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TrimLevelRepository extends JpaRepository<TrimLevel, Integer> {

  List<TrimLevel> findByModelId(Integer modelId);

  List<TrimLevel> findByIsOption(Boolean isOption);

  Optional<TrimLevel> findByNameAndModelId(String name, Integer modelId);

  @Query("SELECT t FROM TrimLevel t WHERE t.model.id = :modelId AND t.isOption = false")
  List<TrimLevel> findBaseTrimLevelsByModelId(@Param("modelId") Integer modelId);
}