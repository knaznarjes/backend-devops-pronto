package com.example.back_end.service;

import com.example.back_end.model.VersionProjet;
import com.example.back_end.repository.VersionProjetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VersionProjetService {

    @Autowired
    private VersionProjetRepository versionProjetRepository;

    // Méthodes existantes

    public List<VersionProjet> getAllVersions() {
        return versionProjetRepository.findAll();
    }

    public VersionProjet getVersionById(String id) {
        return versionProjetRepository.findById(id).orElse(null);
    }

    public VersionProjet createVersion(VersionProjet versionProjet) {
        return versionProjetRepository.save(versionProjet);
    }

    public VersionProjet updateVersion(VersionProjet versionProjet) {
        if (versionProjetRepository.existsById(versionProjet.getId())) {
            return versionProjetRepository.save(versionProjet);
        }
        return null;
    }

    public boolean deleteVersion(String id) {
        if (versionProjetRepository.existsById(id)) {
            versionProjetRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Nouvelle méthode pour récupérer les tâches associées à une version
    public List<String> getTachesByVersionId(String id) {
        // Implémenter la logique pour obtenir les tâches associées à une version
        // Exemple de logique (à adapter en fonction de votre modèle de données)
        // return tacheRepository.findTachesByVersionId(id);
        return List.of(); // Remplacez ceci par la logique réelle
    }

    // Nouvelle méthode pour récupérer le projet associé à une version
    public String getProjetByVersionId(String id) {
        // Implémenter la logique pour obtenir le projet associé à une version
        // Exemple de logique (à adapter en fonction de votre modèle de données)
        // VersionProjet version = versionProjetRepository.findById(id).orElse(null);
        // return version != null ? version.getProjetId() : null;
        return null; // Remplacez ceci par la logique réelle
    }
}
