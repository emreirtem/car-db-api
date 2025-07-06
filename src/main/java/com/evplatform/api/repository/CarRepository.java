package com.evplatform.api.repository;

import com.evplatform.api.model.entity.Car;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {

  List<Car> findByYear(Integer year);

  List<Car> findByModelId(Integer modelId);

  List<Car> findByGenerationId(Integer generationId);

  List<Car> findByTrimLevelId(Integer trimLevelId);

  @Query("SELECT c FROM Car c WHERE c.year BETWEEN :startYear AND :endYear")
  List<Car> findByYearRange(@Param("startYear") Integer startYear, @Param("endYear") Integer endYear);

  @Query("SELECT c FROM Car c WHERE c.model.brand.id = :brandId")
  List<Car> findByBrandId(@Param("brandId") Integer brandId);
}
