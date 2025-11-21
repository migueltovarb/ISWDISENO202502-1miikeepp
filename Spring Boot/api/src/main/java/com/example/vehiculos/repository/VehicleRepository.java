package com.example.vehiculos.repository;

import com.example.vehiculos.model.Vehicle;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface VehicleRepository extends MongoRepository<Vehicle, String> {
  Optional<Vehicle> findByPlateNumber(String plateNumber);
}

