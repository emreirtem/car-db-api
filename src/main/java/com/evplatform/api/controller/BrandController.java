package com.evplatform.api.controller;

import com.evplatform.api.service.BrandService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/brand")
@RequiredArgsConstructor
public class BrandController {

  private final BrandService brandService;

  @GetMapping("/list")
  public List<String> getBrands() {
    return brandService.getBrands();
  }
}
