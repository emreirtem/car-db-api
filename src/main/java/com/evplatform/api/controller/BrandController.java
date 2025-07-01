package com.evplatform.api.controller;

import com.evplatform.api.model.dto.BrandDto;
import com.evplatform.api.service.BrandService;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/brands")
@RequiredArgsConstructor
public class BrandController {

  private final BrandService brandService;

  @GetMapping
  public ResponseEntity<List<BrandDto>> getBrands() {
    List<BrandDto> brands = brandService.getAllBrands();
    return ResponseEntity.ok(brands);
  }

  @PostMapping
  public ResponseEntity<?> createBrand(@RequestBody BrandDto brandRequest) {
    try {
      BrandDto savedBrand = brandService.createBrand(brandRequest);
      return ResponseEntity.status(HttpStatus.CREATED).body(savedBrand);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(Collections.singletonMap("error", e.getMessage()));
    }
  }

  @PutMapping
  public ResponseEntity<?> updateBrand(@RequestBody BrandDto brandRequest) {
    try {
      BrandDto updatedBrand = brandService.updateBrand(brandRequest);
      return ResponseEntity.ok(updatedBrand);
    } catch (NoSuchElementException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(Collections.singletonMap("error", e.getMessage()));
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(Collections.singletonMap("error", e.getMessage()));
    }
  }
}
