package com.example.vehiculos.service;

import com.example.vehiculos.model.Vehicle;
import com.example.vehiculos.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {
  private final VehicleRepository repository;

  public VehicleService(VehicleRepository repository) {
    this.repository = repository;
  }

  public List<Vehicle> findAll() {
    return repository.findAll();
  }

  public Optional<Vehicle> findById(String id) {
    return repository.findById(id);
  }

  public Optional<Vehicle> findByPlate(String plate) {
    return repository.findByPlateNumber(plate);
  }

  public Vehicle create(Vehicle v) {
    return repository.save(v);
  }

  public Optional<Vehicle> update(String id, Vehicle data) {
    return repository.findById(id).map(existing -> {
      existing.setPlateNumber(data.getPlateNumber());
      existing.setBrand(data.getBrand());
      existing.setModel(data.getModel());
      existing.setYear(data.getYear());
      existing.setColor(data.getColor());
      return repository.save(existing);
    });
  }

  public boolean delete(String id) {
    if (repository.existsById(id)) {
      repository.deleteById(id);
      return true;
    }
    return false;
  }
}

