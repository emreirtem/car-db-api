package com.evplatform.api.repository;

import com.evplatform.api.model.entity.Model;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ModelRepository extends JpaRepository<Model, Integer> {

  List<Model> findByBrandId(Integer brandId);

  Optional<Model> findByNameAndBrandId(String name, Integer brandId);

  @Query("SELECT m FROM Model m WHERE LOWER(m.name) LIKE LOWER(CONCAT('%', :name, '%'))")
  List<Model> findByNameContainingIgnoreCase(@Param("name") String name);

  boolean existsByNameAndBrandId(String name, Integer brandId);
}
