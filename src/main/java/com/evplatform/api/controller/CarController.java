package com.evplatform.api.controller;

import com.evplatform.api.model.dto.CarDto;
import com.evplatform.api.model.entity.Car;
import com.evplatform.api.service.CarService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/car")
@RequiredArgsConstructor
public class CarController {

  private final CarService carService;

  @GetMapping
  public ResponseEntity<List<CarDto>> getCars() {
    return ResponseEntity.ok(
        carService.findAll().stream()
            .map(CarDto::ofWithAllInfo)
            .toList()
    );
  }

  @GetMapping("/{id}")
  public ResponseEntity<CarDto> getCarById(@PathVariable Integer id) {
    return ResponseEntity.ok(
        CarDto.ofWithAllInfo(
            carService.findById(id)
        )
    );
  }

  @GetMapping("/year/{year}")
  public ResponseEntity<List<CarDto>> getCarsByYear(@PathVariable Integer year) {
    return ResponseEntity.ok(
        carService.findByYear(year).stream()
            .map(CarDto::ofWithAllInfo)
            .toList()
    );
  }

  @GetMapping("/year-range")
  public ResponseEntity<List<CarDto>> getCarsByYearRange(
      @RequestParam Integer startYear,
      @RequestParam Integer endYear) {
    return ResponseEntity.ok(
        carService.findByYearRange(startYear, endYear).stream()
            .map(CarDto::ofWithAllInfo)
            .toList()
    );
  }

  @GetMapping("/model/{modelId}")
  public ResponseEntity<List<CarDto>> getCarsByModelId(@PathVariable Integer modelId) {
    return ResponseEntity.ok(
        carService.findByModelId(modelId).stream()
            .map(CarDto::of)
            .toList()
    );
  }

  @GetMapping("/brand/{brandId}")
  public ResponseEntity<List<CarDto>> getCarsByBrandId(@PathVariable Integer brandId) {
    return ResponseEntity.ok(
        carService.findByBrandId(brandId).stream()
            .map(CarDto::ofWithAllInfo)
            .toList()
    );
  }


  @PostMapping
  public ResponseEntity<CarDto> createCar(@RequestBody CarDto carDto) {
    var savedCar = carService.save(Car.of(carDto));

    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(savedCar.getId())
        .toUri();

    return ResponseEntity.created(location)
        .body(CarDto.of(savedCar));
  }

  @PutMapping
  public ResponseEntity<CarDto> updateCar(@RequestBody CarDto carDto) {
    return ResponseEntity.ok(
        CarDto.of(
            carService.update(carDto.getId(), Car.of(carDto))
        )
    );
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCar(@PathVariable Integer id) {
    carService.deleteById(id);
    return ResponseEntity.ok().build();
  }
}