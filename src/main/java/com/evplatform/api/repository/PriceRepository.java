package com.evplatform.api.repository;

import com.evplatform.api.model.entity.Price;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceRepository extends JpaRepository<Price, Integer> {

  List<Price> findByPriceTypeType(String priceType);

  List<Price> findByRefId(Integer refId);

  List<Price> findByPriceTypeTypeAndRefId(String priceType, Integer refId);

  @Query("SELECT p FROM Price p WHERE p.date BETWEEN :startDate AND :endDate")
  List<Price> findByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

  @Query("SELECT p FROM Price p WHERE p.priceType.type = :priceType AND p.refId = :refId ORDER BY p.date DESC")
  Optional<Price> findLatestByPriceTypeAndRefId(@Param("priceType") String priceType, @Param("refId") Integer refId);
}
