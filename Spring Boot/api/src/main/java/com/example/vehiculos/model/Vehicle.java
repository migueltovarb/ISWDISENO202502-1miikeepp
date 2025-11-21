package com.example.vehiculos.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Document(collection = "vehicles")
public class Vehicle {
  @Id
  private String id;

  @NotBlank
  @Size(max = 20)
  private String plateNumber;

  @NotBlank
  @Size(max = 50)
  private String brand;

  @NotBlank
  @Size(max = 50)
  private String model;

  @Min(1900)
  private int year;

  @NotBlank
  @Size(max = 30)
  private String color;

  public Vehicle() {}

  public Vehicle(String id, String plateNumber, String brand, String model, int year, String color) {
    this.id = id;
    this.plateNumber = plateNumber;
    this.brand = brand;
    this.model = model;
    this.year = year;
    this.color = color;
  }

  public String getId() { return id; }
  public void setId(String id) { this.id = id; }

  public String getPlateNumber() { return plateNumber; }
  public void setPlateNumber(String plateNumber) { this.plateNumber = plateNumber; }

  public String getBrand() { return brand; }
  public void setBrand(String brand) { this.brand = brand; }

  public String getModel() { return model; }
  public void setModel(String model) { this.model = model; }

  public int getYear() { return year; }
  public void setYear(int year) { this.year = year; }

  public String getColor() { return color; }
  public void setColor(String color) { this.color = color; }
}

