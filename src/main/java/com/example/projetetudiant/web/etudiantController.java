package com.example.projetetudiant.web;

import com.example.projetetudiant.entities.departement;
import com.example.projetetudiant.entities.employe;
import com.example.projetetudiant.entities.etudiant;
import com.example.projetetudiant.entities.matiere;
import com.example.projetetudiant.repositories.etudiantRepository;
import com.example.projetetudiant.security.entities.appUser;
import com.example.projetetudiant.security.services.iservice;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class etudiantController {
    private etudiantRepository etudiantRepository;
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
        etudiant.setUsername(appUser.getUsername());
        serviceImpl.addUser(appUser.getUsername(),appUser.getPassword(),appUser.getPassword());
        serviceImpl.addRoleToUser(appUser.getUsername(),"ETUDIANT" );
        etudiantRepository.save(etudiant);
        System.out.println("user" + appUser.getUsername());
        return "redirect:/SignEtudiant";
    }

    @GetMapping("/SignEtudiant")
    public String SignEtudiant(Model model){
        return "SignEtudiant.html";
    }
    @GetMapping("/etudiant/InterfaceEtudiant")
    public String InterfaceEtudiant(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // Utilisez le nom d'utilisateur pour récupérer l'étudiant connecté à partir de votre repository ou service
        etudiant etudiant = etudiantRepository.findByUsername(username);

        if (etudiant != null) {
            model.addAttribute("etudiant", etudiant);
            return "InterfaceEtudiant.html";
        } else {
            return "error";
        }
    }



}