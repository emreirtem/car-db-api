package com.evplatform.api.controller;

import com.evplatform.api.model.dto.ModelDto;
import com.evplatform.api.service.ModelService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/model")
@RequiredArgsConstructor
public class ModelController {

  private final ModelService modelService;

  @GetMapping
  public ResponseEntity<List<ModelDto>> getBrands() {
    return ResponseEntity.ok(
        modelService.findAll().stream()
            .map(modelService::toModelDto)
            .toList()
    );
  }

  @GetMapping("brand/{brandId}")
  public ResponseEntity<List<ModelDto>> getModelsByBrandId(@PathVariable Integer id) {
    return ResponseEntity.ok(
        modelService.findByBrandId(id).stream()
            .map(modelService::toModelDto)
            .toList()
    );
  }


}
