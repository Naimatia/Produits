package com.example.projetetudiant.dto;

public class MatiereDTO {
    private Long id;
    private String nom;

    public MatiereDTO(Long id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
