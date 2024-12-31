package com.example.back_end.controller;

import com.example.back_end.model.RecommandationTache;
import com.example.back_end.service.RecommandationTacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/recommandations/tache")
public class RecommandationTacheController {

    @Autowired
    private RecommandationTacheService recommandationTacheService;

    @GetMapping
    public List<RecommandationTache> getAllRecommandationTache() {
        return recommandationTacheService.getAllRecommandationTache();
    }

    @GetMapping("/{id}")
    public RecommandationTache getRecommandationTacheById(@PathVariable String id) {
        return recommandationTacheService.getRecommandationTacheById(id);
    }

    @PostMapping
    public RecommandationTache createRecommandationTache(@RequestBody RecommandationTache recommandationTache) {
        return recommandationTacheService.createRecommandationTache(recommandationTache);
    }

    @PutMapping("/{id}")
    public RecommandationTache updateRecommandationTache(@PathVariable String id, @RequestBody RecommandationTache recommandationTache) {
        recommandationTache.setId(id);
        return recommandationTacheService.updateRecommandationTache(recommandationTache);
    }

    @DeleteMapping("/{id}")
    public void deleteRecommandationTache(@PathVariable String id) {
        recommandationTacheService.deleteRecommandationTache(id);
    }
}
