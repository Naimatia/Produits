package com.example.projetetudiant.web;

import com.example.projetetudiant.entities.professeur;
import com.example.projetetudiant.repositories.ProfesseurRepository;
import com.example.projetetudiant.security.entities.appUser;
import com.example.projetetudiant.security.services.iservice;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
public class professeurController {
    private final ProfesseurRepository professeurRepository;
    @Autowired
    private iservice serviceImpl;

    @GetMapping("/admin/home")
    public String home(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
                       @RequestParam(name = "size", defaultValue = "6") int size,
                       @RequestParam(name = "key", defaultValue = "") String key
    ) {
        Page<professeur> professeurPage = professeurRepository.findAllByNomContains(key, PageRequest.of(page, size));
        model.addAttribute("pages", professeurPage.getContent());
        model.addAttribute("nbrPages", new int[professeurPage.getTotalPages()]);
        model.addAttribute("key", key);
        model.addAttribute("pageCurrent", page);
        return "home";
    }

    @GetMapping(value = "/admin/delete")
    public String delete(Long id, int page, String key) {

        professeur professeur = professeurRepository.findById(id).orElse(null);
        if (professeur != null) {
            // Delete user and associated roles
            serviceImpl.deleteUserAndRoles(professeur.getNom());

            // Delete professeur
            professeurRepository.delete(professeur);
        }

        return "redirect:/admin/home?page=" + page + "&key=" + key;
    }

    @GetMapping("/admin/add")
    public String add(Model model) {
        model.addAttribute("professeur", new professeur());
        model.addAttribute("appUser", new appUser());
        model.addAttribute("roles", serviceImpl.getAllRoles());
        return "add";
    }

    @PostMapping("/admin/save")
    public String save(Model model, @Valid professeur professeur, BindingResult bindingResult,
                       @Valid appUser appUser, BindingResult userBindingResult) {
        String roleName;
        if (bindingResult.hasErrors() || userBindingResult.hasErrors()) {
            return "add";
        }

        professeurRepository.save(professeur);
        appUser.setUsername(professeur.getNom()); // set the username to the value of nom

        appUser.setPassword(appUser.getPasswordEncoder().encode(appUser.getPassword()));
        serviceImpl.addUser(appUser.getUsername(), appUser.getPassword(), appUser.getPassword());
        roleName = String.valueOf(professeur.getRole());
        serviceImpl.addRoleToUser(appUser.getUsername(), roleName);

        return "redirect:/admin/home";
    }

    @GetMapping("/admin/edit")
    public String edit(Model model, @RequestParam Long id) {
        professeur professeur = professeurRepository.findById(id).orElse(null);
        if (professeur == null) throw new RuntimeException("Professeur not found");

        return "edit";
    }
}
