package com.evplatform.api.controller;

import com.evplatform.api.model.dto.FeatureGroupDto;
import com.evplatform.api.model.entity.FeatureGroup;
import com.evplatform.api.service.FeatureGroupService;
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
@RequestMapping("/feature/group")
@RequiredArgsConstructor
public class FeatureGroupController {

  private final FeatureGroupService featureGroupService;

  @GetMapping
  public List<FeatureGroupDto> getAll() {
    return featureGroupService.findAll().stream()
        .map(FeatureGroupDto::of)
        .toList();
  }

  @GetMapping("/{id}")
  public FeatureGroupDto getById(@PathVariable Integer id) {
    return FeatureGroupDto.of(
        featureGroupService.findById(id)
    );
  }

  @PostMapping
  public FeatureGroupDto create(@RequestBody FeatureGroupDto featureGroupDto) {
    return FeatureGroupDto.of(
        featureGroupService.save(FeatureGroup.of(featureGroupDto))
    );
  }

  @PutMapping
  public FeatureGroupDto update(@RequestBody FeatureGroupDto featureGroupDto) {
    return FeatureGroupDto.of(
        featureGroupService.update(featureGroupDto.getId(), FeatureGroup.of(featureGroupDto))
    );
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Integer id) {
    featureGroupService.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}