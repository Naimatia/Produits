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
   public String add(Model model){
       model.addAttribute("employe",new employe());
       model.addAttribute("appUser",new appUser());
       model.addAttribute("departements", departementRepository.findAll());
       model.addAttribute("matieres", matiereRepository.findAll());
       model.addAttribute("classes", classeRepository.findAll());
       model.addAttribute("roles", serviceImpl.getAllRoles());
       return "add";
   }
    @PostMapping("/admin/save")
    public String save(Model model, @Valid employe employe, BindingResult bindingResult,
                       @Valid appUser appUser, BindingResult userBindingResult,
                       @RequestParam("departement") Long departementId,
                       @RequestParam("matiere") Long matiereId,
                       @RequestParam("classe") Long classeId){
        String roleName;
        if (bindingResult.hasErrors() || userBindingResult.hasErrors()){
            return "add";
        }
        departement departement = departementRepository.findById(departementId).orElse(null);
        matiere matiere = matiereRepository.findById(matiereId).orElse(null);
        classe classe = classeRepository.findById(classeId).orElse(null);


        // Set the departement and matiere to the employe entity
        employe.setDepartement(departement);
        employe.setMatiere(matiere);
        employe.setClasse(classe);
        // add user
        appUser.setUsername(employe.getNom()); // set the username to the value of nom
      //  appUser.setPassword(appUser.getPasswordEncoder().encode(appUser.getPassword()));
        serviceImpl.addUser(appUser.getUsername(), appUser.getPassword(), appUser.getPassword());
            roleName = String.valueOf(employe.getRole());
          serviceImpl.addRoleToUser(appUser.getUsername(),roleName );

        employeRepository.save(employe);
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
    public String edit(Model model,Long id, int page,String key) {
        employe employe = employeRepository.findById(id).orElse(null);
        if (employe == null) throw new RuntimeException("Responsable not found");
        // Load user information
        appUser appUser = serviceImpl.loadUserByUsername(employe.getNom());

        // Set the selected department and matiere in the dropdowns
        model.addAttribute("selectedDepartement", employe.getDepartement().getIddep());
        model.addAttribute("selectedMatiere", employe.getMatiere().getIdMat());
        model.addAttribute("selectedClasse", employe.getClasse().getIdClas());

        if (appUser == null) throw new RuntimeException("User not found");
        // Add user information to model
        model.addAttribute("employe", employe);
        model.addAttribute("appUser", appUser);
        model.addAttribute("departements", departementRepository.findAll());
        model.addAttribute("matieres", matiereRepository.findAll());
        model.addAttribute("classes", classeRepository.findAll());

        model.addAttribute("page",page);
        model.addAttribute("key",key);
        return "edit";
    }

    @PostMapping("/admin/edit")
    public String saveEdit(Model model, @Valid employe employe, BindingResult bindingResult,
                           @Valid appUser appUser, BindingResult userBindingResult,
                           @RequestParam(name = "username") String username,
                           @RequestParam(name = "rolename") String role,
                           @RequestParam(name = "page", defaultValue = "0") int page,
                           @RequestParam(name = "key", defaultValue = "") String key) {

        if (bindingResult.hasErrors() || userBindingResult.hasErrors()) {
            return "edit";
        }
        // Get the original appUser object before the update
        appUser originalAppUser = serviceImpl.loadUserByUsername(username);
        // Set the new username
        originalAppUser.setUsername(employe.getNom());
        System.out.println("nom  "+ originalAppUser.getUsername());
        // Encode the password
        originalAppUser.setPassword(appUser.getPasswordEncoder().encode(appUser.getPassword()));
        serviceImpl.editUser(appUser.getUsername(), originalAppUser.getUsername(), appUser.getPassword(), appUser.getPassword());
        serviceImpl.removeRoleFromUser(originalAppUser.getUsername(),role);
        serviceImpl.addRoleToUser(originalAppUser.getUsername(), String.valueOf(employe.getRole()));
        // Save the employe object
        employeRepository.save(employe);

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
