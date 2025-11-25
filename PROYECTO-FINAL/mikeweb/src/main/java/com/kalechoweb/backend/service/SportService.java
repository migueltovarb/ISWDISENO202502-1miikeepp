package com.kalechoweb.backend.service;

import com.kalechoweb.backend.model.Sport;
import com.kalechoweb.backend.model.User;
import com.kalechoweb.backend.repository.SportRepository;
import com.kalechoweb.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

@Service
public class SportService {

    @Autowired
    private SportRepository sportRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Sport> getAll() {
        return sportRepository.findAll();
    }

    public Optional<Sport> getById(@NonNull String id) {
        return sportRepository.findById(id);
    }

    public Sport create(@NonNull String adminUserId, @NonNull Sport sport) {
        Optional<User> adminOpt = userRepository.findById(adminUserId);
        if (adminOpt.isEmpty() || adminOpt.get().getRole() == null || !adminOpt.get().getRole().equals("ADMIN")) {
            return null;
        }
        return sportRepository.save(sport);
    }

    public Sport update(@NonNull String adminUserId, @NonNull String sportId, @NonNull Sport sport) {
        Optional<User> adminOpt = userRepository.findById(adminUserId);
        if (adminOpt.isEmpty() || adminOpt.get().getRole() == null || !adminOpt.get().getRole().equals("ADMIN")) {
            return null;
        }
        Optional<Sport> existingSport = sportRepository.findById(sportId);
        if (existingSport.isEmpty()) {
            return null;
        }
        sport.setId(sportId);
        return sportRepository.save(sport);
    }

    public boolean delete(@NonNull String adminUserId, @NonNull String sportId) {
        Optional<User> adminOpt = userRepository.findById(adminUserId);
        if (adminOpt.isEmpty() || adminOpt.get().getRole() == null || !adminOpt.get().getRole().equals("ADMIN")) {
            return false;
        }
        if (sportRepository.findById(sportId).isEmpty()) {
            return false;
        }
        sportRepository.deleteById(sportId);
        return true;
    }
}
