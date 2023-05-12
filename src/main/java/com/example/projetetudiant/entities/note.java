package com.example.projetetudiant.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idnote;
    @DecimalMin(value = "0.0", inclusive = false)
    @DecimalMax(value = "20.0", inclusive = false)
    public double ds;
    @DecimalMin(value = "0.0", inclusive = false)
    @DecimalMax(value = "20.0", inclusive = false)
    private double dc;
    @ManyToOne
    @JoinColumn(name = "etudiant_id")
    private etudiant etudiant;

    // Association ManyToOne avec Matiere
    @ManyToOne
    @JoinColumn(name = "matiere_id")
    private matiere matiere;

    public double getDs() {
        return ds;
    }

    public void setDs(double ds) {
        this.ds = ds;
    }

    public double getDc() {
        return dc;
    }

    public void setDc(double dc) {
        this.dc = dc;
    }

    public com.example.projetetudiant.entities.etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(com.example.projetetudiant.entities.etudiant etudiant) {
        this.etudiant = etudiant;
    }

    public com.example.projetetudiant.entities.matiere getMatiere() {
        return matiere;
    }

    public void setMatiere(com.example.projetetudiant.entities.matiere matiere) {
        this.matiere = matiere;
    }
}
