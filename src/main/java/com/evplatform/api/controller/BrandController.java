package com.evplatform.api.controller;

import com.evplatform.api.model.dto.BrandDto;
import com.evplatform.api.model.entity.Brand;
import com.evplatform.api.service.BrandService;
import java.net.URI;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/brand")
@RequiredArgsConstructor
public class BrandController {

  private final BrandService brandService;

  @GetMapping
  public ResponseEntity<List<BrandDto>> getBrands() {
    return ResponseEntity.ok(
        brandService.findAll().stream()
            .map(brandService::toBrandDto)
            .toList()
    );
  }

  @GetMapping("/{id}")
  public ResponseEntity<BrandDto> getBrand(@PathVariable Integer id) {
    return ResponseEntity.ok(
        brandService.toBrandDtoWithModels(
            brandService.findById(id)
        )
    );
  }

  @PostMapping
  public ResponseEntity<BrandDto> createBrand(@RequestBody Brand brandRequest) {

    var savedBrand = brandService.save(brandRequest);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(savedBrand.getId())
        .toUri();

    return ResponseEntity.created(location)
        .body(brandService.toBrandDto(savedBrand));
  }

  @PutMapping
  public ResponseEntity<BrandDto> updateBrand(@RequestBody Brand brandRequest) {
    return ResponseEntity.ok(
        brandService.toBrandDto(
            brandService.update(brandRequest.getId(), brandRequest)
        )
    );
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteBrand(@PathVariable Integer id) {
    brandService.deleteById(id);
    return ResponseEntity.ok().build();
  }
}
