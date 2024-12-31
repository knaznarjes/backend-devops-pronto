package com.example.back_end.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@Document(collection = "personnels")
public class Personnel {

    @Id
    private String id;

    @Field(name="nom")
    private String nom;

    @Field(name="competences")
    private List<String> competences;

    @Field(name="disponibilite")
    private Boolean disponibilite;

    @Field(name="tacheIds")
    private List<String> tacheIds;

    @Field(name="projetIds")
    private List<String> projetIds;

    @Field(name="role")
    private String role;
}