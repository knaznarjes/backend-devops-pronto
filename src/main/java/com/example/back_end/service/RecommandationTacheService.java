package com.example.back_end.service;

import com.example.back_end.model.RecommandationTache;
import java.util.List;

public interface RecommandationTacheService {
    List<RecommandationTache> getAllRecommandationTache();
    RecommandationTache getRecommandationTacheById(String id);
    RecommandationTache createRecommandationTache(RecommandationTache recommandationTache);
    RecommandationTache updateRecommandationTache(RecommandationTache recommandationTache);
    void deleteRecommandationTache(String id);
}
