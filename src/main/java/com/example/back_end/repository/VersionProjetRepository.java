package com.example.back_end.repository;

import com.example.back_end.model.VersionProjet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VersionProjetRepository extends MongoRepository<VersionProjet, String> {

}
