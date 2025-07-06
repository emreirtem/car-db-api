package com.evplatform.api.controller;

import com.evplatform.api.model.dto.ModelDto;
import com.evplatform.api.model.entity.Model;
import com.evplatform.api.service.ModelService;
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
@RequestMapping("/model")
@RequiredArgsConstructor
public class ModelController {

  private final ModelService modelService;

  @GetMapping
  public ResponseEntity<List<ModelDto>> getModels() {
    return ResponseEntity.ok(
        modelService.findAll().stream()
            .map(ModelDto::ofWithBrand)
            .toList()
    );
  }

  @GetMapping("/{modelId}")
  public ResponseEntity<ModelDto> getModelById(@PathVariable Integer modelId) {
    return ResponseEntity.ok(
        ModelDto.ofWithBrand(
            modelService.findById(modelId)
        )
    );
  }

  @GetMapping("brand/{brandId}")
  public ResponseEntity<List<ModelDto>> getModelsByBrandId(@PathVariable Integer brandId) {
    return ResponseEntity.ok(
        modelService.findByBrandId(brandId).stream()
            .map(ModelDto::of)
            .toList()
    );
  }

  @PostMapping
  public ResponseEntity<ModelDto> createModel(@RequestBody ModelDto modelDto) {
    var savedModel = modelService.save(
        Model.ofWithBrand(modelDto)
    );
    ModelDto savedDto = ModelDto.ofWithBrand(savedModel);
    return ResponseEntity.status(201).body(savedDto);
  }

  @PutMapping
  public ResponseEntity<ModelDto> updateModel(@RequestBody ModelDto modelDto) {
    var updatedModel = modelService.update(modelDto.getId(), Model.of(modelDto));
    ModelDto updatedDto = ModelDto.ofWithBrand(updatedModel);
    return ResponseEntity.ok(updatedDto);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteModel(@PathVariable Integer id) {
    modelService.deleteById(id);
    return ResponseEntity.ok().build();
  }

}
