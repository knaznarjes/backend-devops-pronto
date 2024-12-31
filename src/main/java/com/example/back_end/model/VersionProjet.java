package com.example.back_end.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

@Data
@Document(collection = "versions")
public class VersionProjet {

    @Id
    private String id;

    @Field(name="numeroVersion")
    private String numeroVersion;

    @Field(name="dateDebut")
    private Date dateDebut;

    @Field(name="dateFin")
    private Date dateFin;

    @Field(name="technologies")
    private List<String> technologies;

    @Field(name="projetId")
    private String projetId;

    @Field(name="taches")
    private List<String> tacheIds;
}
