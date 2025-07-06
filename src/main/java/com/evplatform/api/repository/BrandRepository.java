package com.evplatform.api.repository;

import com.evplatform.api.model.entity.Brand;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {

  Optional<Brand> findByName(String name);

  @Query("SELECT b FROM Brand b WHERE LOWER(b.name) LIKE LOWER(CONCAT('%', :name, '%'))")
  List<Brand> findByNameContainingIgnoreCase(@Param("name") String name);

  boolean existsByNameIgnoreCase(String name);
}
