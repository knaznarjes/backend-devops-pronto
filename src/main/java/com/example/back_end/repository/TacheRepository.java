package com.example.back_end.repository;

import com.example.back_end.model.Tache;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TacheRepository extends MongoRepository<Tache, String> {

    /**
     * Trouver les tâches par ID de version.
     *
     * @param versionId l'ID de la version
     * @return une liste de tâches associées à l'ID de version
     */
    List<Tache> findByVersionId(String versionId);
}
