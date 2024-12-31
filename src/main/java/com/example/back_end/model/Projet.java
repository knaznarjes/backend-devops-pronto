package com.example.back_end.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

@Data
@Document(collection = "projets")
public class Projet {

    @Id
    private String id;

    @Field(name="nom")
    private String nom;

    @Field(name="description")
    private String description;

    @Field(name="dateDebut")
    private Date dateDebut;

    @Field(name="dateLimite")
    private Date dateLimite;

    @Field(name="status")
    private String status;

    @Field(name="versions")
    private List<String> versionIds;

    @Field(name="personnels")
    private List<String> personnelIds;

}
