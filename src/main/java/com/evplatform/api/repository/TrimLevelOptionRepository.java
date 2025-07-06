package com.evplatform.api.repository;

import com.evplatform.api.model.entity.TrimLevelOption;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TrimLevelOptionRepository extends JpaRepository<TrimLevelOption, Integer> {

  List<TrimLevelOption> findByTrimLevelId(Integer trimLevelId);

  List<TrimLevelOption> findByOptionId(Integer optionId);

  @Query("SELECT tlo FROM TrimLevelOption tlo WHERE tlo.trimLevel.id = :trimLevelId")
  List<TrimLevelOption> findOptionsByTrimLevelId(@Param("trimLevelId") Integer trimLevelId);
}
