package com.example.projetetudiant.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class departement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long iddep;
    @NotEmpty
    @Size(min = 4,max = 15)
    private String nom;


    @OneToMany(mappedBy = "departement")
    private List<employe> employes;

}
