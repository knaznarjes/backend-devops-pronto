package com.example.back_end.service;

import com.example.back_end.model.Projet;
import com.example.back_end.model.Tache;
import com.example.back_end.model.VersionProjet;
import com.example.back_end.repository.ProjetRepository;
import com.example.back_end.repository.TacheRepository;
import com.example.back_end.repository.VersionProjetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TacheService {

    @Autowired
    private TacheRepository tacheRepository;

    @Autowired
    private VersionProjetRepository versionProjetRepository;

    @Autowired
    private ProjetRepository projetRepository;

    public List<Tache> getAllTaches() {
        return tacheRepository.findAll();
    }

    public Tache getTacheById(String id) {
        return tacheRepository.findById(id).orElse(null);
    }

    public Tache createTache(Tache tache) {
        return tacheRepository.save(tache);
    }

    public Tache updateTache(Tache tache) {
        if (tacheRepository.existsById(tache.getId())) {
            return tacheRepository.save(tache);
        }
        return null;
    }

    public boolean deleteTache(String id) {
        if (tacheRepository.existsById(id)) {
            tacheRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Tache addTaskToVersion(String versionId, Tache tache) {
        VersionProjet version = versionProjetRepository.findById(versionId).orElse(null);
        if (version != null) {
            tache.setVersionId(versionId); // Associe la tâche à la version
            Tache createdTache = tacheRepository.save(tache);
            version.getTacheIds().add(createdTache.getId());
            versionProjetRepository.save(version);
            return createdTache;
        }
        return null;
    }
    public boolean deleteTacheFromVersion(String versionId, String tacheId) {
        VersionProjet version = versionProjetRepository.findById(versionId).orElse(null);
        if (version != null) {
            if (version.getTacheIds().contains(tacheId)) {
                version.getTacheIds().remove(tacheId);  // Remove the task ID from the version
                versionProjetRepository.save(version);

                tacheRepository.deleteById(tacheId);  // Delete the task from the database
                return true;
            }
        }
        return false;
    }


    public Tache createTacheInVersion(String versionId, String projetId, Tache tache) {
        VersionProjet version = versionProjetRepository.findById(versionId).orElse(null);
        Projet projet = projetRepository.findById(projetId).orElse(null);

        if (version == null || projet == null) {
            return null;
        }

        if (!projet.getVersionIds().contains(versionId)) {
            return null;
        }

        tache.setVersionId(versionId); // Associe la tâche à la version
        Tache createdTache = tacheRepository.save(tache);

        version.getTacheIds().add(createdTache.getId());
        projet.getVersionIds().add(versionId);

        versionProjetRepository.save(version);
        projetRepository.save(projet);

        return createdTache;
    }

    public List<Tache> getTachesByVersionId(String versionId) {
        return tacheRepository.findByVersionId(versionId);
    }
}
