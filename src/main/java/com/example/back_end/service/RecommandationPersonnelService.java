package com.example.back_end.service;

import com.example.back_end.model.RecommandationPersonnel;
import java.util.List;

public interface RecommandationPersonnelService {
    List<RecommandationPersonnel> getAllRecommandationPersonnel();
    RecommandationPersonnel getRecommandationPersonnelById(String id);
    RecommandationPersonnel createRecommandationPersonnel(RecommandationPersonnel recommandationPersonnel);
    RecommandationPersonnel updateRecommandationPersonnel(RecommandationPersonnel recommandationPersonnel);
    void deleteRecommandationPersonnel(String id);
}
