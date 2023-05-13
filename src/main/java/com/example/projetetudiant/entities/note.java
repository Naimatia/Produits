package com.example.projetetudiant.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idnote;
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "20.0")
    public double ds;
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "20.0")
    private double dc;
    @ManyToOne
    @JoinColumn(name = "etudiant_id")
    private etudiant etudiant;

    // Association ManyToOne avec Matiere
    @ManyToOne
    @JoinColumn(name = "matiere_id")
    private matiere matiere;

}
