package com.example.back_end.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@Document(collection = "recommandations_personnel")
public class RecommandationPersonnel {

    @Id
    private String id;

    @Field(name = "nom_personnel")
    private String nomPersonnel;

    @Field(name = "competences")
    private List<String> competences;

    @Field(name = "disponibilite")
    private String disponibilite;
}
