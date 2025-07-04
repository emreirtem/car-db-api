package com.evplatform.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloWorldController {

  @GetMapping("/say")
  public String sayHello() {
    return "Hello, World!";
  }
}
