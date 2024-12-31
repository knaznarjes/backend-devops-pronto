package com.example.back_end.controller;

import com.example.back_end.model.VersionProjet;
import com.example.back_end.service.VersionProjetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/versions")
public class VersionProjetController {

    @Autowired
    private VersionProjetService versionProjetService;

    @GetMapping
    public List<VersionProjet> getAllVersions() {
        return versionProjetService.getAllVersions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<VersionProjet> getVersionById(@PathVariable String id) {
        VersionProjet versionProjet = versionProjetService.getVersionById(id);
        if (versionProjet == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(versionProjet);
    }

    @PostMapping
    public ResponseEntity<VersionProjet> createVersion(@RequestBody VersionProjet versionProjet) {
        VersionProjet createdVersion = versionProjetService.createVersion(versionProjet);
        return ResponseEntity.ok(createdVersion);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VersionProjet> updateVersion(@PathVariable String id, @RequestBody VersionProjet versionProjet) {
        versionProjet.setId(id);
        VersionProjet updatedVersion = versionProjetService.updateVersion(versionProjet);
        if (updatedVersion == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedVersion);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVersion(@PathVariable String id) {
        boolean deleted = versionProjetService.deleteVersion(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/details/{versionId}")
    public ResponseEntity<VersionProjet> getVersionDetails(@PathVariable String versionId) {
        VersionProjet versionProjet = versionProjetService.getVersionById(versionId);
        if (versionProjet == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(versionProjet);
    }

    // les tâches associées a une version
    @GetMapping("/{id}/taches")
    public ResponseEntity<List<String>> getTachesByVersionId(@PathVariable String id) {
        List<String> tacheIds = versionProjetService.getTachesByVersionId(id);
        if (tacheIds == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tacheIds);
    }

    // le projet associé a une version
    @GetMapping("/{id}/projet")
    public ResponseEntity<String> getProjetByVersionId(@PathVariable String id) {
        String projetId = versionProjetService.getProjetByVersionId(id);
        if (projetId == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(projetId);
    }
}
