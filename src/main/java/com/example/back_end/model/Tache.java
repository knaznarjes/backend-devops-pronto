package com.example.back_end.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

@Data
@Document(collection = "taches")
public class Tache {

    @Id
    private String id;

    @Field(name = "description")
    private String description;

    @Field(name = "dateDebut")
    private Date dateDebut;

    @Field(name = "dateFin")
    private Date dateFin;

    @Field(name = "status")
    private String status;

    @Field(name = "versionId")
    private String versionId;

    @Field(name = "personnelIds")
    private List<String> personnelIds;
}
