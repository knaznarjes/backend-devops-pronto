package com.example.back_end.repository;

import com.example.back_end.model.Personnel;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface PersonnelRepository extends MongoRepository<Personnel, String> {

    List<Personnel> findByNomContaining(String nom);

    List<Personnel> findByProjetIdsContaining(String projetId);
}
