package com.example.back_end.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


@Getter
@Setter
@Document(collection = "Signup")
public class SignupDTO {
    @NotBlank
    @Size(min = 3, max = 30)
    @Field(name="username")
    private String username;



    @NotBlank
    @Size(min = 6, max = 60)
    @Field(name="password")
    private String password;
}