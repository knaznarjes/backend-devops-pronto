package com.example.back_end.controller;

import com.example.back_end.model.Personnel;
import com.example.back_end.model.Projet;
import com.example.back_end.model.Tache;
import com.example.back_end.model.VersionProjet;
import com.example.back_end.service.ProjetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projets")
public class ProjetController {

    @Autowired
    private ProjetService projetService;

    @GetMapping
    public List<Projet> getAllProjets() {
        return projetService.getAllProjets();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Projet> getProjetById(@PathVariable String id) {
        Projet projet = projetService.getProjetById(id);
        if (projet == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(projet);
    }

    @PostMapping
    public ResponseEntity<Projet> createProjet(@RequestBody Projet projet) {
        Projet createdProjet = projetService.createProjet(projet);
        return ResponseEntity.ok(createdProjet);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Projet> updateProjet(@PathVariable String id, @RequestBody Projet projet) {
        projet.setId(id);
        Projet updatedProjet = projetService.updateProjet(projet);
        if (updatedProjet == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedProjet);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProjet(@PathVariable String id) {
        boolean deleted = projetService.deleteProjet(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/personnels")
    public ResponseEntity<List<Personnel>> getPersonnelsByProjetId(@PathVariable String id) {
        List<Personnel> personnels = projetService.getPersonnelsByProjetId(id);
        if (personnels == null || personnels.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(personnels);
    }

    @GetMapping("/{id}/versions")
    public ResponseEntity<List<VersionProjet>> getVersionsByProjetId(@PathVariable String id) {
        List<VersionProjet> versions = projetService.getVersionsByProjetId(id);
        if (versions == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(versions);
    }

    @GetMapping("/versions/{versionId}")
    public ResponseEntity<VersionProjet> getVersionById(@PathVariable String versionId) {
        VersionProjet version = projetService.getVersionById(versionId);
        if (version == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(version);
    }

    @PostMapping("/{projetId}/versions/{versionId}")
    public ResponseEntity<Projet> addVersionToProjet(@PathVariable String projetId, @PathVariable String versionId) {
        Projet updatedProjet = projetService.addVersionToProjet(projetId, versionId);
        if (updatedProjet == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedProjet);
    }

    @PostMapping("/{projetId}/versions")
    public ResponseEntity<VersionProjet> createVersionForProjet(@PathVariable String projetId, @RequestBody VersionProjet versionProjet) {
        VersionProjet createdVersion = projetService.createVersionForProjet(projetId, versionProjet);
        if (createdVersion == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(createdVersion);
    }

    @PostMapping("/{projetId}/versions/{versionId}/taches")
    public ResponseEntity<Tache> createTaskForVersionForProjet(@PathVariable String projetId,
                                                               @PathVariable String versionId,
                                                               @RequestBody Tache task) {
        Tache createdTask = projetService.createTaskForVersionForProjet(projetId, versionId, task);
        if (createdTask == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(createdTask);
    }
}
