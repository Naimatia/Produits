package com.example.projetetudiant.entities;

public class professeur {
    private String nom;
    private String prenom;
    private String matiereEnseignee;
    private String adresse;
    private String telephone;

    public professeur() {
        this.nom = nom;
        this.prenom = prenom;
        this.matiereEnseignee = matiereEnseignee;
        this.adresse = adresse;
        this.telephone = telephone;
    }

    // Getters et setters

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getMatiereEnseignee() {
        return matiereEnseignee;
    }

    public void setMatiereEnseignee(String matiereEnseignee) {
        this.matiereEnseignee = matiereEnseignee;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getRole()
    {
        return "Professeur";
    }

}

