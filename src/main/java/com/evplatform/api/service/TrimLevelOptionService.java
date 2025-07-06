package com.evplatform.api.service;

import com.evplatform.api.model.entity.TrimLevel;
import com.evplatform.api.model.entity.TrimLevelOption;
import com.evplatform.api.repository.TrimLevelOptionRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class TrimLevelOptionService {

  private final TrimLevelOptionRepository trimLevelOptionRepository;
  private final TrimLevelService trimLevelService;

  public List<TrimLevelOption> findAll() {
    log.debug("Finding all trim level options");
    return trimLevelOptionRepository.findAll();
  }

  public Page<TrimLevelOption> findAll(Pageable pageable) {
    log.debug("Finding all trim level options with pagination: {}", pageable);
    return trimLevelOptionRepository.findAll(pageable);
  }

  public TrimLevelOption findById(Integer id) {
    log.debug("Finding trim level option by id: {}", id);
    return trimLevelOptionRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Trim level option not found with id: " + id));
  }

  public List<TrimLevelOption> findByTrimLevelId(Integer trimLevelId) {
    log.debug("Finding trim level options by trim level id: {}", trimLevelId);
    return trimLevelOptionRepository.findByTrimLevelId(trimLevelId);
  }

  public List<TrimLevelOption> findByOptionId(Integer optionId) {
    log.debug("Finding trim level options by option id: {}", optionId);
    return trimLevelOptionRepository.findByOptionId(optionId);
  }

  public List<TrimLevelOption> findOptionsByTrimLevelId(Integer trimLevelId) {
    log.debug("Finding options by trim level id: {}", trimLevelId);
    return trimLevelOptionRepository.findOptionsByTrimLevelId(trimLevelId);
  }

  @Transactional
  public TrimLevelOption save(TrimLevelOption trimLevelOption) {
    log.debug("Saving trim level option for trim level: {} and option: {}",
        trimLevelOption.getTrimLevel().getId(), trimLevelOption.getOption().getId());

    // Validate trim level exists
    TrimLevel trimLevel = trimLevelService.findById(trimLevelOption.getTrimLevel().getId());
    TrimLevel option = trimLevelService.findById(trimLevelOption.getOption().getId());

    // Validate that option is actually an option (isOption = true)
    if (!option.getIsOption()) {
      throw new IllegalArgumentException("Referenced option must have isOption = true");
    }

    // Validate that option belongs to the same model as trim level
    if (!trimLevel.getModel().getId().equals(option.getModel().getId())) {
      throw new IllegalArgumentException("Option must belong to the same model as the trim level");
    }

    trimLevelOption.setTrimLevel(trimLevel);
    trimLevelOption.setOption(option);

    return trimLevelOptionRepository.save(trimLevelOption);
  }

  @Transactional
  public TrimLevelOption update(Integer id, TrimLevelOption optionDetails) {
    log.debug("Updating trim level option with id: {}", id);

    TrimLevelOption existingOption = findById(id);

    // If trim level is being changed, validate it
    if (optionDetails.getTrimLevel() != null &&
        !existingOption.getTrimLevel().getId().equals(optionDetails.getTrimLevel().getId())) {
      TrimLevel newTrimLevel = trimLevelService.findById(optionDetails.getTrimLevel().getId());
      existingOption.setTrimLevel(newTrimLevel);
    }

    // If option is being changed, validate it
    if (optionDetails.getOption() != null &&
        !existingOption.getOption().getId().equals(optionDetails.getOption().getId())) {
      TrimLevel newOption = trimLevelService.findById(optionDetails.getOption().getId());

      if (!newOption.getIsOption()) {
        throw new IllegalArgumentException("Referenced option must have isOption = true");
      }

      if (!existingOption.getTrimLevel().getModel().getId().equals(newOption.getModel().getId())) {
        throw new IllegalArgumentException("Option must belong to the same model as the trim level");
      }

      existingOption.setOption(newOption);
    }

    return trimLevelOptionRepository.save(existingOption);
  }

  @Transactional
  public void deleteById(Integer id) {
    log.debug("Deleting trim level option with id: {}", id);

    TrimLevelOption option = findById(id);
    trimLevelOptionRepository.deleteById(id);
  }
}