package com.evplatform.api.service;

import com.evplatform.api.model.entity.Car;
import com.evplatform.api.model.entity.Generation;
import com.evplatform.api.model.entity.Model;
import com.evplatform.api.model.entity.TrimLevel;
import com.evplatform.api.repository.CarRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CarService {

  private final CarRepository carRepository;
  private final ModelService modelService;
  private final GenerationService generationService;
  private final TrimLevelService trimLevelService;

  public List<Car> findAll() {
    log.debug("Finding all cars");
    return carRepository.findAll();
  }

  public Page<Car> findAll(Pageable pageable) {
    log.debug("Finding all cars with pagination: {}", pageable);
    return carRepository.findAll(pageable);
  }

  public Car findById(Integer id) {
    log.debug("Finding car by id: {}", id);
    return carRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Car not found with id: " + id));
  }

  public List<Car> findByYear(Integer year) {
    log.debug("Finding cars by year: {}", year);
    return carRepository.findByYear(year);
  }

  public List<Car> findByYearRange(Integer startYear, Integer endYear) {
    log.debug("Finding cars by year range: {} - {}", startYear, endYear);
    return carRepository.findByYearRange(startYear, endYear);
  }

  public List<Car> findByModelId(Integer modelId) {
    log.debug("Finding cars by model id: {}", modelId);
    return carRepository.findByModelId(modelId);
  }

  public List<Car> findByBrandId(Integer brandId) {
    log.debug("Finding cars by brand id: {}", brandId);
    return carRepository.findByBrandId(brandId);
  }

  public List<Car> findByGenerationId(Integer generationId) {
    log.debug("Finding cars by generation id: {}", generationId);
    return carRepository.findByGenerationId(generationId);
  }

  public List<Car> findByTrimLevelId(Integer trimLevelId) {
    log.debug("Finding cars by trim level id: {}", trimLevelId);
    return carRepository.findByTrimLevelId(trimLevelId);
  }

  public Car save(Car car) {
    log.debug("Saving car: year={}, model={}", car.getYear(), car.getModel().getName());

    validateCarData(car);
    return carRepository.save(car);
  }

  public Car update(Integer id, Car carDetails) {
    log.debug("Updating car with id: {}", id);

    Car existingCar = findById(id);
    //validateCarData(carDetails); // Transactional bir sorun var

    existingCar.setYear(carDetails.getYear());
    existingCar.setModel(carDetails.getModel());
    existingCar.setGeneration(carDetails.getGeneration());
    existingCar.setTrimLevel(carDetails.getTrimLevel());

    return carRepository.save(existingCar);
  }

  public void deleteById(Integer id) {
    log.debug("Deleting car with id: {}", id);

    Car car = findById(id);
    carRepository.deleteById(id);
  }

  private void validateCarData(Car car) {
    if (car.getYear() == null || car.getYear() < 1900 || car.getYear() > 2100) {
      throw new IllegalArgumentException("Invalid year: " + car.getYear());
    }

    if (car.getModel() == null || car.getModel().getId() == null) {
      throw new IllegalArgumentException("Model is required");
    }

    if (car.getGeneration() == null || car.getGeneration().getId() == null) {
      throw new IllegalArgumentException("Generation is required");
    }

    if (car.getTrimLevel() == null || car.getTrimLevel().getId() == null) {
      throw new IllegalArgumentException("Trim level is required");
    }

    // Validate relationships
    Model model = modelService.findById(car.getModel().getId());
    Generation generation = generationService.findById(car.getGeneration().getId());
    TrimLevel trimLevel = trimLevelService.findById(car.getTrimLevel().getId());

    if (!generation.getModel().getId().equals(model.getId())) {
      throw new IllegalArgumentException("Generation does not belong to the specified model");
    }

    if (!trimLevel.getModel().getId().equals(model.getId())) {
      throw new IllegalArgumentException("Trim level does not belong to the specified model");
    }
  }
}
