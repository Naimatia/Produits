package com.example.projetetudiant.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

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
    private List<etudiant> etudiant;
    @OneToMany(mappedBy = "departement")
    private List<classe> classes;
    @ManyToMany(mappedBy = "departements")
    private List<employe> employes;

    @Override
    public String toString() {
        return "departement{" +
                "iddep=" + iddep +
                ", nom='" + nom + '\'' +
                ", etudiant=" + etudiant +
                ", classes=" + classes +
                ", employes=" + employes +
                '}';
    }
}
