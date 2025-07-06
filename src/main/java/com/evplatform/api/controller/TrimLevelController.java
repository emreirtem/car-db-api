package com.evplatform.api.controller;

import com.evplatform.api.model.dto.TrimLevelDto;
import com.evplatform.api.model.entity.TrimLevel;
import com.evplatform.api.service.TrimLevelService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trim-level")
@RequiredArgsConstructor
public class TrimLevelController {

  private final TrimLevelService trimLevelService;

  @GetMapping
  public ResponseEntity<List<TrimLevelDto>> getTrimLevels() {
    return ResponseEntity.ok(
        trimLevelService.findAll().stream()
            .map(TrimLevelDto::ofWithModelAndBrand)
            .toList()
    );
  }

  @GetMapping("/{trimLevelId}")
  public ResponseEntity<TrimLevelDto> getTrimLevelById(@PathVariable Integer trimLevelId) {
    return ResponseEntity.ok(
        TrimLevelDto.ofWithModelAndBrand(
            trimLevelService.findById(trimLevelId)
        )
    );
  }

  @GetMapping("model/{modelId}")
  public ResponseEntity<List<TrimLevelDto>> getTrimLevelsByModelId(@PathVariable Integer modelId) {
    return ResponseEntity.ok(
        trimLevelService.findByModelId(modelId).stream()
            .map(TrimLevelDto::ofWithModel)
            .toList()
    );
  }

  @PostMapping
  public ResponseEntity<TrimLevelDto> createTrimLevel(@RequestBody TrimLevelDto trimLevelDto) {

    var savedModel = trimLevelService.save(
        TrimLevel.ofWithModel(trimLevelDto)
    );
    TrimLevelDto savedDto = TrimLevelDto.ofWithModelAndBrand(savedModel);
    return ResponseEntity.status(201).body(savedDto);
  }

  @PutMapping
  public ResponseEntity<TrimLevelDto> updateTrimLevel(@RequestBody TrimLevelDto trimLevelDto) {
    return ResponseEntity.ok(
        TrimLevelDto.of(
            trimLevelService.update(trimLevelDto.getId(), TrimLevel.of(trimLevelDto))
        )
    );
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteGeneration(@PathVariable Integer id) {
    trimLevelService.deleteById(id);
    return ResponseEntity.ok().build();
  }

}
