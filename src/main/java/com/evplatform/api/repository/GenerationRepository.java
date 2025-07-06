package com.evplatform.api.repository;

import com.evplatform.api.model.entity.Generation;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GenerationRepository extends JpaRepository<Generation, Integer> {

  List<Generation> findByModelId(Integer modelId);

  Optional<Generation> findByNameAndModelId(String name, Integer modelId);

  @Query("SELECT g FROM Generation g WHERE g.model.id = :modelId ORDER BY g.faceliftDate DESC")
  List<Generation> findByModelIdOrderByFaceliftDateDesc(@Param("modelId") Integer modelId);
}
