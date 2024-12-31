package com.example.back_end.controller;

import com.example.back_end.model.Personnel;
import com.example.back_end.service.PersonnelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/personnels")
public class PersonnelController {

    @Autowired
    private PersonnelService personnelService;

    @GetMapping
    public List<Personnel> getAllPersonnel(@RequestParam(value = "search", required = false) String search) {
        if (search != null && !search.isEmpty()) {
            return personnelService.searchPersonnel(search);
        } else {
            return personnelService.getAllPersonnel();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Personnel> getPersonnelById(@PathVariable String id) {
        Personnel personnel = personnelService.getPersonnelById(id);
        if (personnel == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(personnel);
    }
    //  les personnels par ID de projet
    @GetMapping("/by-projet/{projetId}")
    public ResponseEntity<List<Personnel>> getPersonnelsByProjetId(@PathVariable String projetId) {
        List<Personnel> personnels = personnelService.getPersonnelsByProjetId(projetId);
        if (personnels == null || personnels.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(personnels);
    }
    @PostMapping
    public ResponseEntity<Personnel> createPersonnel(@RequestBody Personnel personnel) {
        Personnel createdPersonnel = personnelService.createPersonnel(personnel);
        return ResponseEntity.ok(createdPersonnel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Personnel> updatePersonnel(@PathVariable String id, @RequestBody Personnel personnel) {
        personnel.setId(id);
        Personnel updatedPersonnel = personnelService.updatePersonnel(personnel);
        if (updatedPersonnel == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedPersonnel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePersonnel(@PathVariable String id) {
        boolean deleted = personnelService.deletePersonnel(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    //  les projets associés à un personnel
    @GetMapping("/{id}/projets")
    public ResponseEntity<List<String>> getProjetsByPersonnelId(@PathVariable String id) {
        List<String> projetIds = personnelService.getProjetsByPersonnelId(id);
        if (projetIds == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(projetIds);
    }

    //  les tâches associées à un personnel
    @GetMapping("/{id}/taches")
    public ResponseEntity<List<String>> getTachesByPersonnelId(@PathVariable String id) {
        List<String> tacheIds = personnelService.getTachesByPersonnelId(id);
        if (tacheIds == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tacheIds);
    }
}
