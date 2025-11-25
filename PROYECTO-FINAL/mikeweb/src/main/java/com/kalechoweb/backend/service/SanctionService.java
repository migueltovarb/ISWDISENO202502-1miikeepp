package com.kalechoweb.backend.service;

import com.kalechoweb.backend.model.Sanction;
import com.kalechoweb.backend.model.User;
import com.kalechoweb.backend.repository.SanctionRepository;
import com.kalechoweb.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SanctionService {

    @Autowired
    private SanctionRepository sanctionRepository;

    @Autowired
    private UserRepository userRepository;

    public Sanction addSanction(@NonNull String adminUserId, @NonNull Sanction sanction) {
        Optional<User> adminOpt = userRepository.findById(adminUserId);
        if (adminOpt.isEmpty() || adminOpt.get().getRole() == null || !adminOpt.get().getRole().equals("ADMIN")) {
            return null;
        }
        String studentId = sanction.getStudentId();
        if (studentId == null || studentId.isBlank()) {
            return null;
        }
        Optional<User> studentOpt = userRepository.findById(studentId);
        if (studentOpt.isEmpty() || studentOpt.get().getRole() == null || !studentOpt.get().getRole().equals("STUDENT")) {
            return null;
        }
        sanction.setIssuedAt(LocalDateTime.now());
        sanction.setActive(true);
        return sanctionRepository.save(sanction);
    }

    public List<Sanction> getByStudent(@NonNull String studentId) {
        return sanctionRepository.findByStudentId(studentId);
    }

    public Optional<Sanction> deactivate(@NonNull String adminUserId, @NonNull String sanctionId) {
        Optional<User> adminOpt = userRepository.findById(adminUserId);
        if (adminOpt.isEmpty() || adminOpt.get().getRole() == null || !adminOpt.get().getRole().equals("ADMIN")) {
            return Optional.empty();
        }
        return sanctionRepository.findById(sanctionId).map(s -> {
            s.setActive(false);
            return sanctionRepository.save(s);
        });
    }

    public Sanction addSanctionByEmail(@NonNull String adminUserId, @NonNull String studentEmail, @NonNull String reason, LocalDateTime expiresAt) {
        Optional<User> adminOpt = userRepository.findById(adminUserId);
        if (adminOpt.isEmpty() || adminOpt.get().getRole() == null || !adminOpt.get().getRole().equals("ADMIN")) {
            return null;
        }
        Optional<User> studentOpt = userRepository.findByEmail(studentEmail);
        if (studentOpt.isEmpty() || studentOpt.get().getRole() == null || !studentOpt.get().getRole().equals("STUDENT")) {
            return null;
        }
        Sanction sanction = new Sanction();
        sanction.setStudentId(studentOpt.get().getId());
        sanction.setReason(reason);
        sanction.setIssuedAt(LocalDateTime.now());
        sanction.setExpiresAt(expiresAt);
        sanction.setActive(true);
        return sanctionRepository.save(sanction);
    }
}
