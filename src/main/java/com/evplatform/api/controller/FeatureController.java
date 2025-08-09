package com.evplatform.api.controller;

import com.evplatform.api.model.dto.FeatureDto;
import com.evplatform.api.model.entity.Feature;
import com.evplatform.api.service.FeatureService;
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
@RequestMapping("/feature")
@RequiredArgsConstructor
public class FeatureController {

  private final FeatureService featureService;

  @GetMapping
  public List<FeatureDto> getAll() {
    return featureService.findAll().stream()
        .map(FeatureDto::of)
        .toList();
  }

  @GetMapping("/{id}")
  public FeatureDto getById(@PathVariable Integer id) {
    return FeatureDto.of(featureService.findById(id));
  }

  @GetMapping("/category/{categoryId}")
  public List<FeatureDto> getByCategoryId(@PathVariable Integer categoryId) {
    return featureService.findByFeatureCategoryId(categoryId).stream()
        .map(FeatureDto::of)
        .toList();
  }

  @GetMapping("/group/{groupId}")
  public List<FeatureDto> getByGroupId(@PathVariable Integer groupId) {
    return featureService.findByFeatureGroupIdOrderByOrder(groupId).stream()
        .map(FeatureDto::of)
        .toList();
  }

  @PostMapping
  public FeatureDto create(@RequestBody FeatureDto featureDto) {
    return FeatureDto.of(
        featureService.save(Feature.of(featureDto))
    );
  }

  @PutMapping
  public FeatureDto update(@RequestBody FeatureDto featureDto) {
    return FeatureDto.of(
        featureService.update(featureDto.getId(), Feature.of(featureDto))
    );
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Integer id) {
    featureService.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}