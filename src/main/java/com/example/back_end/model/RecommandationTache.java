package com.example.back_end.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "recommandations_tache")
public class RecommandationTache {

    @Id
    private String id;

    @Field(name = "description")
    private String description;

    @Field(name = "statut")
    private String statut;

    @Field(name = "date_creation")
    private String dateCreation;

    @Field(name = "version_projet_id")
    private String versionProjetId;

    @Field(name = "projet_id")
    private String projetId;
}
