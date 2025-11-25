package com.kalechoweb.backend.controller;

import com.kalechoweb.backend.model.Sanction;
import com.kalechoweb.backend.service.SanctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sanciones")
@CrossOrigin(origins = "*")
public class SanctionController {

    @Autowired
    private SanctionService sanctionService;

    @PostMapping
    public ResponseEntity<Sanction> addSanction(@RequestParam("adminUserId") @NonNull String adminUserId,
                                                @RequestBody @NonNull Sanction sanction) {
        Sanction created = sanctionService.addSanction(adminUserId, sanction);
        if (created == null) {
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok(created);
    }

    @GetMapping("/por-estudiante/{studentId}")
    public List<Sanction> getByStudent(@PathVariable @NonNull String studentId) {
        return sanctionService.getByStudent(studentId);
    }

    @PutMapping("/{id}/desactivar")
    public ResponseEntity<Sanction> deactivate(@PathVariable("id") @NonNull String sanctionId,
                                               @RequestParam("adminUserId") @NonNull String adminUserId) {
        Optional<Sanction> updated = sanctionService.deactivate(adminUserId, sanctionId);
        return updated.map(ResponseEntity::ok).orElse(ResponseEntity.status(403).build());
    }

    @PostMapping("/por-email")
    public ResponseEntity<Sanction> addByEmail(@RequestParam("adminUserId") @NonNull String adminUserId,
                                               @RequestParam("studentEmail") @NonNull String studentEmail,
                                               @RequestParam("reason") @NonNull String reason,
                                               @RequestParam(value = "expiresAt", required = false) String expiresAtStr) {
        java.time.LocalDateTime expiresAt = null;
        if (expiresAtStr != null && !expiresAtStr.isBlank()) {
            try {
                expiresAt = java.time.LocalDateTime.parse(expiresAtStr);
            } catch (Exception ignored) {}
        }
        Sanction s = sanctionService.addSanctionByEmail(adminUserId, studentEmail, reason, expiresAt);
        if (s == null) {
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok(s);
    }
}
