package com.kalechoweb.backend.repository;

import com.kalechoweb.backend.model.Sanction;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface SanctionRepository extends MongoRepository<Sanction, String> {
    List<Sanction> findByStudentId(String studentId);
}
