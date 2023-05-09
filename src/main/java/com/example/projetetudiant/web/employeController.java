package com.example.projetetudiant.web;

import com.example.projetetudiant.entities.employe;
import com.example.projetetudiant.repositories.employeRepository;
import com.example.projetetudiant.security.entities.appUser;
import com.example.projetetudiant.security.services.iservice;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@AllArgsConstructor
public class employeController {
    private final employeRepository employeRepository;

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
       model.addAttribute("roles", serviceImpl.getAllRoles());
       return "add";
   }
    @PostMapping("/admin/save")
    public String save(Model model, @Valid employe employe, BindingResult bindingResult,
                       @Valid appUser appUser, BindingResult userBindingResult){
        String roleName;
        if (bindingResult.hasErrors() || userBindingResult.hasErrors()){
            return "add";
        }
        appUser.setUsername(employe.getNom()); // set the username to the value of nom
      //  appUser.setPassword(appUser.getPasswordEncoder().encode(appUser.getPassword()));
        serviceImpl.addUser(appUser.getUsername(), appUser.getPassword(), appUser.getPassword());
            roleName = String.valueOf(employe.getRole());
          serviceImpl.addRoleToUser(appUser.getUsername(),roleName );

        employeRepository.save(employe);
        System.out.println("Role name: " + roleName);
        System.out.println("ajout employe: " + employe);
        return "redirect:/admin/home";
    }

    @GetMapping("/admin/edit")
    public String edit(Model model,Long id, int page,String key) {
        employe employe = employeRepository.findById(id).orElse(null);
        if (employe == null) throw new RuntimeException("Responsable not found");
        // Load user information
        appUser appUser = serviceImpl.loadUserByUsername(employe.getNom());

        if (appUser == null) throw new RuntimeException("User not found");
        // Add user information to model
        model.addAttribute("employe", employe);
        model.addAttribute("appUser", appUser);
        model.addAttribute("page",page);
        model.addAttribute("key",key);
        return "edit";
    }

    @PostMapping("/admin/edit")
    public String saveEdit(Model model, @Valid employe employe, BindingResult bindingResult,
                           @Valid appUser appUser, BindingResult userBindingResult,
                           @RequestParam(name = "username") String username,
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
