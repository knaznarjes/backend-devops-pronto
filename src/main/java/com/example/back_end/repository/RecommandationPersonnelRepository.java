package com.example.back_end.repository;

import com.example.back_end.model.RecommandationPersonnel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RecommandationPersonnelRepository extends MongoRepository<RecommandationPersonnel, String> {
}
