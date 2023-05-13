package com.example.projetetudiant.web;

import com.example.projetetudiant.entities.*;
import com.example.projetetudiant.repositories.etudiantRepository;
import com.example.projetetudiant.repositories.noteRepository;
import com.example.projetetudiant.repositories.matiereRepository;
import com.example.projetetudiant.security.entities.appUser;
import com.example.projetetudiant.security.services.iservice;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class etudiantController {
    private etudiantRepository etudiantRepository;
    private noteRepository noteRepository;
    private matiereRepository matiereRepository;
    private iservice serviceImpl;
    @GetMapping("/inscription")
    public String inscription(Model model){
        model.addAttribute("etudiant",new etudiant());
        model.addAttribute("appUser",new appUser());
        model.addAttribute("roles",serviceImpl.getAllRoles());
        return "inscription";
    }

    @PostMapping("/save")
    public String save(Model model, @Valid etudiant etudiant,BindingResult bindingResult,@Valid appUser appUser){
        if (bindingResult.hasErrors()) return "inscription";
        appUser.setUsername(etudiant.getNom());
        serviceImpl.addUser(appUser.getUsername(),appUser.getPassword(),appUser.getPassword());
        serviceImpl.addRoleToUser(appUser.getUsername(),"ETUDIANT" );
        etudiantRepository.save(etudiant);
        return "redirect:/SignEtudiant";
    }

    @GetMapping("/SignEtudiant")
    public String SignEtudiant(Model model){
        return "SignEtudiant.html";
    }

    @GetMapping("/etudiant/InterfaceEtudiant")
    public String InterfaceEtudiant(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Récupérer le nom de l'utilisateur connecté
        String nomEtudiantConnecte = authentication.getName();

        // Rechercher l'étudiant correspondant dans la base de données en utilisant le nom
        etudiant etudiant = etudiantRepository.findByNom(nomEtudiantConnecte);

        // Vérifier si l'étudiant existe
        if (etudiant != null) {
            // Ajouter l'étudiant au modèle pour l'affichage des détails
            model.addAttribute("etudiant", etudiant);
        }
        return "InterfaceEtudiant";
    }
    @GetMapping("/etudiant-notes")
    public String showEtudiantNotes(Model model) {
        // Récupérer l'authentification de l'utilisateur connecté
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Récupérer le nom du profil de l'utilisateur connecté
        String nomProfilEtudiantConnecte = authentication.getName();
        // Rechercher l'étudiant correspondant dans la base de données en utilisant le nom de profil
        etudiant etudiant = etudiantRepository.findByNom(nomProfilEtudiantConnecte);

        // Vérifier si l'étudiant existe
        if (etudiant != null) {
            // Récupérer l'ID de l'étudiant
            Long etudiantId = etudiant.getId();

            // Récupérer les notes de l'étudiant en utilisant son ID
            List<note> notesEtudiant = noteRepository.findByEtudiantId(etudiantId);

            // Calculer la moyenne de l'étudiant
            double moyenne = notesEtudiant.stream().mapToDouble(note -> (note.getDs() + 2 * note.getDc()) / 3).average().orElse(0);
            String moyenneFormatted = String.format("%.2f", moyenne);

            // Ajouter les notes de l'étudiant au modèle pour l'affichage
            model.addAttribute("notesEtudiant", notesEtudiant);
            model.addAttribute("moyenneFormatted", moyenneFormatted);

            // Ajouter une variable "admis" ou "refuse" au modèle en fonction de la moyenne de l'étudiant
            if (moyenne < 10) {
                model.addAttribute("admis", false);
            } else {
                model.addAttribute("admis", true);
            }

            return "etudiant-notes.html";
        } else {
            // Gérer le cas où l'étudiant n'a pas été trouvé
            // (redirection vers une page d'erreur, affichage d'un message, etc.)
            return "error-page";
        }
    }

}