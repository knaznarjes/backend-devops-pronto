package com.example.back_end.repository;

import com.example.back_end.model.RecommandationTache;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RecommandationTacheRepository extends MongoRepository<RecommandationTache, String> {
}
