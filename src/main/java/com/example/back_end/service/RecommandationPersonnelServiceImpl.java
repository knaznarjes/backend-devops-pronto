package com.example.back_end.service;

import com.example.back_end.model.RecommandationPersonnel;
import com.example.back_end.repository.RecommandationPersonnelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RecommandationPersonnelServiceImpl implements RecommandationPersonnelService {

    @Autowired
    private RecommandationPersonnelRepository recommandationPersonnelRepository;

    @Override
    public List<RecommandationPersonnel> getAllRecommandationPersonnel() {
        return recommandationPersonnelRepository.findAll();
    }

    @Override
    public RecommandationPersonnel getRecommandationPersonnelById(String id) {
        Optional<RecommandationPersonnel> optionalRecommandationPersonnel = recommandationPersonnelRepository.findById(id);
        return optionalRecommandationPersonnel.orElse(null);
    }

    @Override
    public RecommandationPersonnel createRecommandationPersonnel(RecommandationPersonnel recommandationPersonnel) {
        return recommandationPersonnelRepository.save(recommandationPersonnel);
    }

    @Override
    public RecommandationPersonnel updateRecommandationPersonnel(RecommandationPersonnel recommandationPersonnel) {
        return recommandationPersonnelRepository.save(recommandationPersonnel);
    }

    @Override
    public void deleteRecommandationPersonnel(String id) {
        recommandationPersonnelRepository.deleteById(id);
    }
}
