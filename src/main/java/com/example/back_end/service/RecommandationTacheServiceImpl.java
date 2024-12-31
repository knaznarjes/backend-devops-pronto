package com.example.back_end.service;

import com.example.back_end.model.RecommandationTache;
import com.example.back_end.repository.RecommandationTacheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RecommandationTacheServiceImpl implements RecommandationTacheService {

    @Autowired
    private RecommandationTacheRepository recommandationTacheRepository;

    @Override
    public List<RecommandationTache> getAllRecommandationTache() {
        return recommandationTacheRepository.findAll();
    }

    @Override
    public RecommandationTache getRecommandationTacheById(String id) {
        Optional<RecommandationTache> optionalRecommandationTache = recommandationTacheRepository.findById(id);
        return optionalRecommandationTache.orElse(null);
    }

    @Override
    public RecommandationTache createRecommandationTache(RecommandationTache recommandationTache) {
        return recommandationTacheRepository.save(recommandationTache);
    }

    @Override
    public RecommandationTache updateRecommandationTache(RecommandationTache recommandationTache) {
        return recommandationTacheRepository.save(recommandationTache);
    }

    @Override
    public void deleteRecommandationTache(String id) {
        recommandationTacheRepository.deleteById(id);
    }
}
