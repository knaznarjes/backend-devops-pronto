package com.example.back_end.service;

import com.example.back_end.model.Personnel;
import com.example.back_end.model.Projet;
import com.example.back_end.model.Tache;
import com.example.back_end.model.VersionProjet;
import com.example.back_end.repository.ProjetRepository;
import com.example.back_end.repository.PersonnelRepository;
import com.example.back_end.repository.TacheRepository;
import com.example.back_end.repository.VersionProjetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProjetService {

    @Autowired
    private ProjetRepository projetRepository;

    @Autowired
    private PersonnelRepository personnelRepository;

    @Autowired
    private VersionProjetRepository versionProjetRepository;

    @Autowired
    private TacheRepository tacheRepository;

    public List<Projet> getAllProjets() {
        return projetRepository.findAll();
    }

    public Projet getProjetById(String id) {
        return projetRepository.findById(id).orElse(null);
    }

    public Projet createProjet(Projet projet) {
        return projetRepository.save(projet);
    }

    public Projet updateProjet(Projet projet) {
        return projetRepository.findById(projet.getId())
                .map(existingProjet -> projetRepository.save(projet))
                .orElse(null);
    }

    public boolean deleteProjet(String id) {
        return projetRepository.findById(id)
                .map(projet -> {
                    projetRepository.deleteById(id);
                    return true;
                })
                .orElse(false);
    }

    public List<Personnel> getPersonnelsByProjetId(String projetId) {
        return projetRepository.findById(projetId)
                .map(projet -> personnelRepository.findAllById(projet.getPersonnelIds()))
                .orElse(null);
    }

    public List<VersionProjet> getVersionsByProjetId(String projetId) {
        return projetRepository.findById(projetId)
                .map(projet -> versionProjetRepository.findAllById(projet.getVersionIds()))
                .orElse(null);
    }

    public VersionProjet getVersionById(String versionId) {
        return versionProjetRepository.findById(versionId).orElse(null);
    }

    public Projet addVersionToProjet(String projetId, String versionId) {
        Optional<Projet> projetOpt = projetRepository.findById(projetId);
        Optional<VersionProjet> versionOpt = versionProjetRepository.findById(versionId);

        if (projetOpt.isPresent() && versionOpt.isPresent()) {
            Projet projet = projetOpt.get();
            VersionProjet version = versionOpt.get();

            List<String> versionIds = projet.getVersionIds();
            if (versionIds == null) {
                versionIds = new ArrayList<>();
                projet.setVersionIds(versionIds);
            }
            versionIds.add(versionId);
            projetRepository.save(projet);

            version.setProjetId(projetId);
            versionProjetRepository.save(version);

            return projet;
        }

        return null;
    }

    public VersionProjet createVersionForProjet(String projetId, VersionProjet versionProjet) {
        return projetRepository.findById(projetId)
                .map(projet -> {
                    VersionProjet createdVersion = versionProjetRepository.save(versionProjet);

                    List<String> versionIds = projet.getVersionIds();
                    if (versionIds == null) {
                        versionIds = new ArrayList<>();
                        projet.setVersionIds(versionIds);
                    }
                    versionIds.add(createdVersion.getId());
                    projetRepository.save(projet);

                    createdVersion.setProjetId(projetId);
                    return versionProjetRepository.save(createdVersion);
                })
                .orElse(null);
    }

    public Tache createTaskForVersionForProjet(String projetId, String versionId, Tache task) {
        Optional<Projet> projetOpt = projetRepository.findById(projetId);
        Optional<VersionProjet> versionOpt = versionProjetRepository.findById(versionId);

        if (projetOpt.isPresent() && versionOpt.isPresent()) {
            VersionProjet version = versionOpt.get();

            task.setVersionId(versionId);
            Tache createdTask = tacheRepository.save(task);

            List<String> tacheIds = version.getTacheIds();
            if (tacheIds == null) {
                tacheIds = new ArrayList<>();
                version.setTacheIds(tacheIds);
            }
            tacheIds.add(createdTask.getId());
            versionProjetRepository.save(version);

            return createdTask;
        }

        return null;
    }

    public boolean deleteTacheFromProjetVersion(String projetId, String versionId, String tacheId) {
        return projetRepository.findById(projetId)
                .flatMap(projet -> versionProjetRepository.findById(versionId)
                        .flatMap(version -> tacheRepository.findById(tacheId)
                                .map(tache -> {
                                    version.getTacheIds().remove(tacheId);
                                    versionProjetRepository.save(version);
                                    tacheRepository.delete(tache);
                                    return true;
                                })
                        )
                ).orElse(false);
    }
}
