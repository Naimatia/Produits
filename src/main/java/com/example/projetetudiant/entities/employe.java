package com.example.projetetudiant.entities;

import com.example.projetetudiant.EnumType.role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class employe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    @Size(min = 2,max = 8)
    private String nom;
    @Size(min = 2,max = 10)
    private String prenom;
    @Email
    private String email;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateDebutTravail;
    @Enumerated(EnumType.STRING)
    private role role;


    public void setRole(role role) {
        this.role = role;
    }
    @ManyToMany
    @JoinTable(name = "employe_departement",
            joinColumns = @JoinColumn(name = "employe_id"),
            inverseJoinColumns = @JoinColumn(name = "departement_id"))
    private List<departement> departements;

    @ManyToMany
    @JoinTable(name = "employe_matiere",
            joinColumns = @JoinColumn(name = "employe_id"),
            inverseJoinColumns = @JoinColumn(name = "matiere_id"))
    private List<matiere> matieres;

    @ManyToMany
    @JoinTable(name = "employe_classe",
            joinColumns = @JoinColumn(name = "employe_id"),
            inverseJoinColumns = @JoinColumn(name = "classe_id"))
    private List<classe> classes;



}
