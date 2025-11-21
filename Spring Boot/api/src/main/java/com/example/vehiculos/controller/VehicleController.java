package com.example.vehiculos.controller;

import com.example.vehiculos.model.Vehicle;
import com.example.vehiculos.service.VehicleService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
@CrossOrigin(origins = "*")
public class VehicleController {
  private final VehicleService service;

  public VehicleController(VehicleService service) {
    this.service = service;
  }

  @GetMapping
  public List<Vehicle> all() {
    return service.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Vehicle> byId(@PathVariable("id") String id) {
    return service.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<Vehicle> create(@Valid @RequestBody Vehicle vehicle) {
    Vehicle saved = service.create(vehicle);
    return ResponseEntity.created(URI.create("/api/vehicles/" + saved.getId())).body(saved);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Vehicle> update(@PathVariable("id") String id, @Valid @RequestBody Vehicle vehicle) {
    return service.update(id, vehicle).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable("id") String id) {
    return service.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
  }
}

