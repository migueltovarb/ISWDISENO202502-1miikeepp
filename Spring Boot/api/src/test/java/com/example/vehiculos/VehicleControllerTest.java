package com.example.vehiculos;

import com.example.vehiculos.controller.VehicleController;
import com.example.vehiculos.model.Vehicle;
import com.example.vehiculos.service.VehicleService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = VehicleController.class)
class VehicleControllerTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private VehicleService service;

  @Test
  void listAll() throws Exception {
    Mockito.when(service.findAll()).thenReturn(List.of(new Vehicle("1","ABC123","Toyota","Corolla",2020,"Rojo")));
    mvc.perform(get("/api/vehicles"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$[0].id").value("1"))
      .andExpect(jsonPath("$[0].brand").value("Toyota"));
  }

  @Test
  void createVehicle() throws Exception {
    Vehicle input = new Vehicle(null, "XYZ987", "Ford", "Fiesta", 2018, "Azul");
    Vehicle saved = new Vehicle("2", input.getPlateNumber(), input.getBrand(), input.getModel(), input.getYear(), input.getColor());
    Mockito.when(service.create(Mockito.any())).thenReturn(saved);

    mvc.perform(post("/api/vehicles")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"plateNumber\":\"XYZ987\",\"brand\":\"Ford\",\"model\":\"Fiesta\",\"year\":2018,\"color\":\"Azul\"}"))
      .andExpect(status().isCreated())
      .andExpect(header().string("Location", "/api/vehicles/2"))
      .andExpect(jsonPath("$.id").value("2"));
  }

  @Test
  void updateVehicle() throws Exception {
    Vehicle updated = new Vehicle("1", "ABC123", "Toyota", "Yaris", 2021, "Negro");
    Mockito.when(service.update(Mockito.eq("1"), Mockito.any())).thenReturn(Optional.of(updated));

    mvc.perform(put("/api/vehicles/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"plateNumber\":\"ABC123\",\"brand\":\"Toyota\",\"model\":\"Yaris\",\"year\":2021,\"color\":\"Negro\"}"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.model").value("Yaris"));
  }

  @Test
  void getByIdNotFound() throws Exception {
    Mockito.when(service.findById("nope")).thenReturn(Optional.empty());
    mvc.perform(get("/api/vehicles/nope"))
      .andExpect(status().isNotFound());
  }

  @Test
  void deleteVehicle() throws Exception {
    Mockito.when(service.delete("1")).thenReturn(true);
    mvc.perform(delete("/api/vehicles/1"))
      .andExpect(status().isNoContent());
  }
}

