package com.evplatform.api.controller;

import com.evplatform.api.model.dto.FeatureCategoryDto;
import com.evplatform.api.model.entity.FeatureCategory;
import com.evplatform.api.service.FeatureCategoryService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feature/category")
@RequiredArgsConstructor
public class FeatureCategoryController {

  private final FeatureCategoryService featureCategoryService;

  /**
   * Get all feature categories
   */
  @GetMapping
  public ResponseEntity<List<FeatureCategoryDto>> getAllFeatureCategories() {
    return ResponseEntity.ok(
        featureCategoryService.findAll().stream()
            .map(FeatureCategoryDto::of)
            .toList());
  }


  @GetMapping("/{id}")
  public ResponseEntity<FeatureCategoryDto> getFeatureCategoryById(@PathVariable Integer id) {
    FeatureCategory category = featureCategoryService.findById(id);
    return ResponseEntity.ok(
        FeatureCategoryDto.of(category)
    );
  }

  @GetMapping("/search")
  public ResponseEntity<FeatureCategoryDto> getFeatureCategoryByName(@RequestParam String name) {
    Optional<FeatureCategory> category = featureCategoryService.findByName(name);
    return category
        .map(FeatureCategoryDto::of)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping("/exists")
  public ResponseEntity<Boolean> existsByName(@RequestParam String name) {
    boolean exists = featureCategoryService.existsByName(name);
    return ResponseEntity.ok(exists);
  }

  @PostMapping
  public ResponseEntity<FeatureCategoryDto> createFeatureCategory(@Valid @RequestBody FeatureCategoryDto featureCategory) {

    if (featureCategory.getId() != null) {
      return ResponseEntity.badRequest().build();
    }

    FeatureCategory category = featureCategoryService.save(FeatureCategory.of(featureCategory));
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(FeatureCategoryDto.of(category));
  }


  @PutMapping
  public ResponseEntity<FeatureCategoryDto> updateFeatureCategory(
      @Valid @RequestBody FeatureCategoryDto featureCategoryDto) {

    FeatureCategory category = featureCategoryService.update(featureCategoryDto.getId(), FeatureCategory.of(featureCategoryDto));
    return ResponseEntity.ok(FeatureCategoryDto.of(category));
  }


  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteFeatureCategory(@PathVariable Integer id) {
    featureCategoryService.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}