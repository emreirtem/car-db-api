package com.evplatform.api.controller;

import com.evplatform.api.model.dto.PriceDto;
import com.evplatform.api.model.entity.Price;
import com.evplatform.api.service.PriceService;
import java.net.URI;
import java.time.LocalDate;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/price")
@RequiredArgsConstructor
public class PriceController {

  private final PriceService priceService;

  @GetMapping
  public ResponseEntity<List<PriceDto>> getPrices() {
    return ResponseEntity.ok(
        priceService.findAll().stream()
            .map(PriceDto::of)
            .toList()
    );
  }

  @GetMapping("/{id}")
  public ResponseEntity<PriceDto> getPriceById(@PathVariable Integer id) {
    return ResponseEntity.ok(
        PriceDto.of(
            priceService.findById(id)
        )
    );
  }

  @GetMapping("/type/{priceType}")
  public ResponseEntity<List<PriceDto>> getPricesByType(@PathVariable String priceType) {
    return ResponseEntity.ok(
        priceService.findByPriceType(priceType).stream()
            .map(PriceDto::of)
            .toList()
    );
  }

  @GetMapping("/ref/{refId}")
  public ResponseEntity<List<PriceDto>> getPricesByRefId(@PathVariable Integer refId) {
    return ResponseEntity.ok(
        priceService.findByRefId(refId).stream()
            .map(PriceDto::of)
            .toList()
    );
  }

  @GetMapping("/type/{priceType}/ref/{refId}")
  public ResponseEntity<List<PriceDto>> getPricesByTypeAndRefId(
      @PathVariable String priceType,
      @PathVariable Integer refId) {
    return ResponseEntity.ok(
        priceService.findByPriceTypeAndRefId(priceType, refId).stream()
            .map(PriceDto::of)
            .toList()
    );
  }

  @GetMapping("/date-range")
  public ResponseEntity<List<PriceDto>> getPricesByDateRange(
      @RequestParam LocalDate startDate,
      @RequestParam LocalDate endDate) {
    return ResponseEntity.ok(
        priceService.findByDateRange(startDate, endDate).stream()
            .map(PriceDto::of)
            .toList()
    );
  }

  @GetMapping("/latest/type/{priceType}/ref/{refId}")
  public ResponseEntity<PriceDto> getLatestPriceByTypeAndRefId(
      @PathVariable String priceType,
      @PathVariable Integer refId) {
    return priceService.findLatestByPriceTypeAndRefId(priceType, refId)
        .map(PriceDto::of)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<PriceDto> createPrice(@RequestBody PriceDto priceDto) {
    var savedPrice = priceService.save(Price.of(priceDto));

    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(savedPrice.getId())
        .toUri();

    return ResponseEntity.created(location)
        .body(PriceDto.of(savedPrice));
  }

  @PutMapping
  public ResponseEntity<PriceDto> updatePrice(@RequestBody PriceDto priceDto) {
    return ResponseEntity.ok(
        PriceDto.of(
            priceService.update(priceDto.getId(), Price.of(priceDto))
        )
    );
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletePrice(@PathVariable Integer id) {
    priceService.deleteById(id);
    return ResponseEntity.ok().build();
  }
}