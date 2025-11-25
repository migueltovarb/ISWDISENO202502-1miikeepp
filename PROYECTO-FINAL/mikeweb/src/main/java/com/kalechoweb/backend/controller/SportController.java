package com.kalechoweb.backend.controller;

import com.kalechoweb.backend.model.Sport;
import com.kalechoweb.backend.service.SportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/deportes")
@CrossOrigin(origins = "*")
public class SportController {

    @Autowired
    private SportService sportService;

    @GetMapping
    public List<Sport> getAll() {
        return sportService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sport> getById(@PathVariable @NonNull String id) {
        Optional<Sport> s = sportService.getById(id);
        return s.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Sport> create(@RequestParam("adminUserId") @NonNull String adminUserId,
                                        @RequestBody @NonNull Sport sport) {
        Sport created = sportService.create(adminUserId, sport);
        if (created == null) {
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sport> update(@PathVariable("id") @NonNull String sportId,
                                        @RequestParam("adminUserId") @NonNull String adminUserId,
                                        @RequestBody @NonNull Sport sport) {
        Sport updated = sportService.update(adminUserId, sportId, sport);
        if (updated == null) {
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") @NonNull String sportId,
                                       @RequestParam("adminUserId") @NonNull String adminUserId) {
        boolean ok = sportService.delete(adminUserId, sportId);
        if (!ok) {
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.noContent().build();
    }
}
