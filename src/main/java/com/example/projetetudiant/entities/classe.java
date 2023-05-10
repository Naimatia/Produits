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
public class classe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idClas;
    @NotEmpty
    @Size(min = 4,max = 15)
    private String nom;

    @ManyToOne
    private departement departement;
    @OneToMany(mappedBy = "classe")
    private List<matiere> matieres;
}
