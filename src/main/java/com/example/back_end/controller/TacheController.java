package com.example.back_end.controller;

import com.example.back_end.model.Tache;
import com.example.back_end.service.TacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/taches")
public class TacheController {

    @Autowired
    private TacheService tacheService;

    @GetMapping
    public List<Tache> getAllTaches() {
        return tacheService.getAllTaches();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tache> getTacheById(@PathVariable String id) {
        Tache tache = tacheService.getTacheById(id);
        if (tache == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tache);
    }

    @PostMapping
    public ResponseEntity<Tache> createTache(@RequestBody Tache tache) {
        Tache createdTache = tacheService.createTache(tache);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTache);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tache> updateTache(@PathVariable String id, @RequestBody Tache tache) {
        tache.setId(id);
        Tache updatedTache = tacheService.updateTache(tache);
        if (updatedTache == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedTache);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTache(@PathVariable String id) {
        boolean deleted = tacheService.deleteTache(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/version/{versionId}")
    public ResponseEntity<Tache> addTaskToVersion(@PathVariable String versionId, @RequestBody Tache tache) {
        Tache createdTache = tacheService.addTaskToVersion(versionId, tache);
        if (createdTache == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTache);
    }

    @DeleteMapping("/version/{versionId}/tache/{tacheId}")
    public ResponseEntity<Void> deleteTacheFromVersion(@PathVariable String versionId, @PathVariable String tacheId) {
        boolean deleted = tacheService.deleteTacheFromVersion(versionId, tacheId);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/version/{versionId}")
    public ResponseEntity<List<Tache>> getTachesByVersionId(@PathVariable String versionId) {
        List<Tache> taches = tacheService.getTachesByVersionId(versionId);
        if (taches == null || taches.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(taches);
    }
}
