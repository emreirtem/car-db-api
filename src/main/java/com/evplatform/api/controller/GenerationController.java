package com.evplatform.api.controller;

import com.evplatform.api.model.dto.GenerationDto;
import com.evplatform.api.model.entity.Generation;
import com.evplatform.api.service.GenerationService;
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
@RequestMapping("/generation")
@RequiredArgsConstructor
public class GenerationController {

  private final GenerationService generationService;

  @GetMapping
  public ResponseEntity<List<GenerationDto>> getGenerations() {
    return ResponseEntity.ok(
        generationService.findAll().stream()
            .map(GenerationDto::ofWithModel)
            .toList()
    );
  }

  @GetMapping("/{generationId}")
  public ResponseEntity<GenerationDto> getGenerationById(@PathVariable Integer generationId) {
    return ResponseEntity.ok(
        GenerationDto.ofWithModelAndBrand(
            generationService.findById(generationId)
        )
    );
  }

  @GetMapping("model/{modelId}")
  public ResponseEntity<List<GenerationDto>> getGenerationsByModelId(@PathVariable Integer modelId) {
    return ResponseEntity.ok(
        generationService.findByModelId(modelId).stream()
            .map(GenerationDto::ofWithModel)
            .toList()
    );
  }

  @PostMapping
  public ResponseEntity<GenerationDto> createGeneration(@RequestBody GenerationDto generationDto) {
    var savedModel = generationService.save(
        Generation.ofWithModel(generationDto)
    );
    GenerationDto savedDto = GenerationDto.ofWithModelAndBrand(savedModel);
    return ResponseEntity.status(201).body(savedDto);
  }

  @PutMapping
  public ResponseEntity<GenerationDto> updateGeneration(@RequestBody GenerationDto generationDto) {
    return ResponseEntity.ok(
        GenerationDto.of(
            generationService.update(generationDto.getId(), Generation.of(generationDto))
        )
    );
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteGeneration(@PathVariable Integer id) {
    generationService.deleteById(id);
    return ResponseEntity.ok().build();
  }

}
