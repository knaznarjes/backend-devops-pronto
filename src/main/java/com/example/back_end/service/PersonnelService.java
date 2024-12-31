package com.example.back_end.service;

import com.example.back_end.model.Personnel;
import com.example.back_end.repository.PersonnelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonnelService {

    @Autowired
    private PersonnelRepository personnelRepository;

    public List<Personnel> getAllPersonnel() {
        return personnelRepository.findAll();
    }

    public Personnel getPersonnelById(String id) {
        return personnelRepository.findById(id).orElse(null);
    }
    // Nouvelle méthode pour obtenir les personnels par ID de projet
    public List<Personnel> getPersonnelsByProjetId(String projetId) {
        return personnelRepository.findByProjetIdsContaining(projetId);
    }
    public Personnel createPersonnel(Personnel personnel) {
        return personnelRepository.save(personnel);
    }

    public Personnel updatePersonnel(Personnel personnel) {
        if (personnelRepository.existsById(personnel.getId())) {
            return personnelRepository.save(personnel);
        }
        return null;
    }

    public boolean deletePersonnel(String id) {
        if (personnelRepository.existsById(id)) {
            personnelRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Nouvelle méthode pour récupérer les projets associés à un personnel
    public List<String> getProjetsByPersonnelId(String id) {
        // Implémenter la logique pour obtenir les projets associés à ce personnel
        // Exemple de logique (à adapter en fonction de votre modèle de données)
        // return projetRepository.findProjetsByPersonnelId(id);
        return List.of(); // Remplacez ceci par la logique réelle
    }

    // Nouvelle méthode pour récupérer les tâches associées à un personnel
    public List<String> getTachesByPersonnelId(String id) {
        // Implémenter la logique pour obtenir les tâches associées à ce personnel
        // Exemple de logique (à adapter en fonction de votre modèle de données)
        // return tacheRepository.findTachesByPersonnelId(id);
        return List.of(); // Remplacez ceci par la logique réelle
    }

    public List<Personnel> searchPersonnel(String search) {
        return personnelRepository.findByNomContaining(search); // Utilisez le bon nom de méthode
    }
}
