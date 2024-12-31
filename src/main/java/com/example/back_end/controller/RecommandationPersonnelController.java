package com.example.back_end.controller;

import com.example.back_end.model.RecommandationPersonnel;
import com.example.back_end.service.RecommandationPersonnelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/recommandations/personnel")
public class RecommandationPersonnelController {

    @Autowired
    private RecommandationPersonnelService recommandationPersonnelService;

    @GetMapping
    public List<RecommandationPersonnel> getAllRecommandationPersonnel() {
        return recommandationPersonnelService.getAllRecommandationPersonnel();
    }

    @GetMapping("/{id}")
    public RecommandationPersonnel getRecommandationPersonnelById(@PathVariable String id) {
        return recommandationPersonnelService.getRecommandationPersonnelById(id);
    }

    @PostMapping
    public RecommandationPersonnel createRecommandationPersonnel(@RequestBody RecommandationPersonnel recommandationPersonnel) {
        return recommandationPersonnelService.createRecommandationPersonnel(recommandationPersonnel);
    }

    @PutMapping("/{id}")
    public RecommandationPersonnel updateRecommandationPersonnel(@PathVariable String id, @RequestBody RecommandationPersonnel recommandationPersonnel) {
        recommandationPersonnel.setId(id);
        return recommandationPersonnelService.updateRecommandationPersonnel(recommandationPersonnel);
    }

    @DeleteMapping("/{id}")
    public void deleteRecommandationPersonnel(@PathVariable String id) {
        recommandationPersonnelService.deleteRecommandationPersonnel(id);
    }
}
