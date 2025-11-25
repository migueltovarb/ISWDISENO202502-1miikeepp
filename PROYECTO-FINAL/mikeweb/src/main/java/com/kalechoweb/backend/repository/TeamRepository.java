package com.kalechoweb.backend.repository;

import com.kalechoweb.backend.model.Team;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface TeamRepository extends MongoRepository<Team, String> {
    List<Team> findBySportId(String sportId);
}
