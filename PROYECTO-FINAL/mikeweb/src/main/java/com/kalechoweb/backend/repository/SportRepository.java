package com.kalechoweb.backend.repository;

import com.kalechoweb.backend.model.Sport;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SportRepository extends MongoRepository<Sport, String> {
}
