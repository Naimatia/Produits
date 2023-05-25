package com.example.projetetudiant.web;

import com.example.projetetudiant.entities.classe;
import com.example.projetetudiant.entities.departement;
import com.example.projetetudiant.entities.employe;
import com.example.projetetudiant.entities.matiere;
import com.example.projetetudiant.repositories.classeRepository;
import com.example.projetetudiant.repositories.departementRepository;
import com.example.projetetudiant.repositories.employeRepository;
import com.example.projetetudiant.repositories.matiereRepository;
import com.example.projetetudiant.security.entities.appUser;
import com.example.projetetudiant.security.services.iservice;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class employeController {
    private final employeRepository employeRepository;
private  departementRepository departementRepository;
private matiereRepository matiereRepository;
private classeRepository classeRepository;
    private iservice serviceImpl;
/*
    @GetMapping("/admin/home")
    public String home(Model model, @RequestParam(name = "page",defaultValue = "0") int page,
                       @RequestParam(name = "size",defaultValue = "6") int size,
                       @RequestParam(name = "key",defaultValue = "") String key
    ){
        Page<employe> responsablePage= employeRepository.findAllByNomContains(key, PageRequest.of(page,size));
        model.addAttribute("pages",responsablePage.getContent());
        model.addAttribute("nbrPages",new int[responsablePage.getTotalPages()]);
        model.addAttribute("key",key);
        model.addAttribute("pageCurrent",page);
        return "home";
    }

 */
@GetMapping("/admin/home")
public String home(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
                   @RequestParam(name = "size", defaultValue = "6") int size,
                   @RequestParam(name = "key", defaultValue = "") String key) {
    Page<employe> responsablePage = employeRepository.findAllByNomContains(key, PageRequest.of(page, size));

    model.addAttribute("pages", responsablePage.getContent());
    model.addAttribute("nbrPages", new int[responsablePage.getTotalPages()]);
    model.addAttribute("key", key);
    model.addAttribute("pageCurrent", page);

    // Fetch individual collections
    for (employe emp : responsablePage.getContent()) {
        emp.setDepartements(departementRepository.findByEmployes(emp));
        emp.setMatieres(matiereRepository.findByEmployes(emp));
        emp.setClasses(classeRepository.findByEmployes(emp));
    }

    return "home";
}

    @GetMapping(value = "/admin/delete")
    public String delete(Long id, int page, String key) {

        employe employe = employeRepository.findById(id).orElse(null);
        if (employe != null) {
            // Delete user and associated roles
            serviceImpl.deleteUserAndRoles(employe.getNom());

            // Delete employe
            employeRepository.deleteById(id);
        }

        return "redirect:/admin/home?page=" + page + "&key=" + key;
    }

    @GetMapping("/admin/add")
    public String add(Model model) {
        model.addAttribute("employe", new employe());
        model.addAttribute("appUser", new appUser());
        model.addAttribute("departements", departementRepository.findAll());
        model.addAttribute("matieres", matiereRepository.findAll());
        model.addAttribute("classes", classeRepository.findAll());
        model.addAttribute("roles", serviceImpl.getAllRoles());
        return "add";
    }

    @PostMapping("/admin/save")
    @Transactional
    public String save(Model model, @Valid employe employe, BindingResult bindingResult,
                       @Valid appUser appUser, BindingResult userBindingResult,
                       @RequestParam("departementIds") List<Long> departementIds,
                       @RequestParam("matiereIds") List<Long> matiereIds,
                       @RequestParam("classeIds") List<Long> classeIds) {
        if (bindingResult.hasErrors() || userBindingResult.hasErrors()) {
            return "add";
        }

        List<departement> departements = departementRepository.findAllById(departementIds);
        List<matiere> matieres = matiereRepository.findAllById(matiereIds);
        List<classe> classes = classeRepository.findAllById(classeIds);

        // Set the departements, matieres, and classes to the employe entity
        employe.setDepartements(departements);
        employe.setMatieres(matieres);
        employe.setClasses(classes);

        // Save the employe entity to the employe table
        employeRepository.save(employe);

        // Add the employe to the departements
        for (departement dept : departements) {
            dept.getEmployes().add(employe);
            departementRepository.save(dept);
        }

        // Add the employe to the classes
        for (classe cl : classes) {
            cl.getEmployes().add(employe);
            classeRepository.save(cl);
        }

        // Add the employe to the matieres
        for (matiere mat : matieres) {
            mat.getEmployes().add(employe);
            matiereRepository.save(mat);
        }

        // Add user
        appUser.setUsername(employe.getNom()); // set the username to the value of nom
        serviceImpl.addUser(appUser.getUsername(), appUser.getPassword(), appUser.getPassword());
        String roleName = String.valueOf(employe.getRole());
        serviceImpl.addRoleToUser(appUser.getUsername(), roleName);

        return "redirect:/admin/home";
    }


    // add a new subject to a class and a new class to a department
@GetMapping("/admin/gestionClasse")
public String gestionClasse(Model model){
    model.addAttribute("departements", departementRepository.findAll());
    model.addAttribute("matieres", matiereRepository.findAll());
    model.addAttribute("classes", classeRepository.findAll()) ;
    return "gestionClasse";
}

@PostMapping("/admin/gestionClasse")
@Transactional
public String gestionClasseSubmit(@RequestParam Long departementId, @RequestParam Long classeId,
                                  @RequestParam(name = "matieres", required = false) String[] matiereIds) {
    // convert string array to list of Longs
    List<Long> matiereIdsList = matiereIds != null ? Arrays.stream(matiereIds)
            .map(Long::parseLong)
            .collect(Collectors.toList()) : new ArrayList<>();

    // find the departement and class entities by their IDs
    departement myDepartement = departementRepository.findById(departementId).orElse(null);
    classe myClasse = classeRepository.findById(classeId).orElse(null);

    if (myDepartement == null || myClasse == null) {
        // handle errors here
        return "gestionClasse";
    }
    // add the selected matieres to the class
    for (Long matiereId : matiereIdsList) {
        matiere matiere = matiereRepository.findById(matiereId).orElse(null);
        if (matiere != null) {
            matiere.setClasse(myClasse);
            myClasse.getMatieres().add(matiere);
        }
    }

    // set the department and add the class to it
    myClasse.setDepartement(myDepartement);
    myDepartement.getClasses().add(myClasse);
    departementRepository.save(myDepartement);
    return "redirect:/admin/home";
}

@GetMapping("/admin/edit")
public String edit(Model model, @RequestParam(name = "id") Long id) {
    employe employe = employeRepository.findById(id).orElse(null);
    if (employe == null) {
        throw new RuntimeException("Employe not found");
    }

    // Load user information
    appUser appUser = serviceImpl.loadUserByUsername(employe.getNom());
    if (appUser == null) {
        throw new RuntimeException("User not found");
    }

    // Fetch specific departments, classes, and matieres associated with the employee
    List<departement> employeDepartements = departementRepository.findByEmployes(employe);
    List<matiere> employeMatieres = matiereRepository.findByEmployes(employe);
    List<classe> employeClasses = classeRepository.findByEmployes(employe);

    // Set the fetched collections to the employe object
    employe.setDepartements(employeDepartements);
    employe.setMatieres(employeMatieres);
    employe.setClasses(employeClasses);

    // Set the selected values in the model for rendering in the view
    model.addAttribute("employe", employe);
    model.addAttribute("appUser", appUser);
    model.addAttribute("departements", employeDepartements);
    model.addAttribute("matieres", employeMatieres);
    model.addAttribute("classes", employeClasses);

    return "edit";
}



    @PostMapping("/admin/edit")
    public String saveEdit(Model model, @Valid employe employe, BindingResult bindingResult,
                           @Valid appUser appUser, BindingResult userBindingResult,
                           @RequestParam(name = "page", defaultValue = "0") int page,
                           @RequestParam(name = "key", defaultValue = "") String key,
                           @RequestParam(name = "selectedDepartements", required = false) List<Long> selectedDepartementIds,
                           @RequestParam(name = "selectedMatieres", required = false) List<Long> selectedMatiereIds,
                           @RequestParam(name = "selectedClasses", required = false) List<Long> selectedClassIds) {

        if (bindingResult.hasErrors() || userBindingResult.hasErrors()) {
            return "edit";
        }

        // Fetch the selected departements, matieres, and classes based on their IDs
        List<departement> selectedDepartements = departementRepository.findAllById(selectedDepartementIds);
        List<matiere> selectedMatieres = matiereRepository.findAllById(selectedMatiereIds);
        List<classe> selectedClasses = classeRepository.findAllById(selectedClassIds);

        // Set the selected departements, matieres, and classes to the employe entity
        employe.setDepartements(selectedDepartements);
        employe.setMatieres(selectedMatieres);
        employe.setClasses(selectedClasses);

        // Save the employe object
        employeRepository.save(employe);

        // Add any additional logic or operations as needed

        return "redirect:/admin/home?page=" + page + "&key=" + key;
    }



    @GetMapping("/")
    public String racine(){
        return "racine.html";
    }
    @GetMapping("/SignUser")
    public String SignUser(Model model){
        return "SignUser.html";
    }
    @GetMapping("/SignEmploye")
    public String SignEmploye(Model model){
        return "SignEmploye.html";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        // perform logout actions, e.g., invalidate session
        request.getSession().invalidate();
        // redirect to login page
        return "redirect:/";
    }
    @GetMapping("/403")
    public String accessFaild(){
        return "403";
    }



}
