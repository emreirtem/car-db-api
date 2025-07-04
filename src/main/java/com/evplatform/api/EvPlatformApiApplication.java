package com.evplatform.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.evplatform.api.model.entity")
@EnableJpaRepositories("com.evplatform.api.repository")
public class EvPlatformApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(EvPlatformApiApplication.class, args);
  }
}
