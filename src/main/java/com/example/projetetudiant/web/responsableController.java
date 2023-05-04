package com.example.projetetudiant.web;

import com.example.projetetudiant.entities.etudiant;
import com.example.projetetudiant.entities.responsable;
import com.example.projetetudiant.repositories.responsableRepository;
import com.example.projetetudiant.security.entities.appRole;
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

import javax.validation.Valid;

@Controller
@AllArgsConstructor
public class responsableController {
    private final responsableRepository responsableRepository;
    @Autowired
    private iservice serviceImpl;

    @GetMapping("/admin/home")
    public String home(Model model, @RequestParam(name = "page",defaultValue = "0") int page,
                       @RequestParam(name = "size",defaultValue = "6") int size,
                       @RequestParam(name = "key",defaultValue = "") String key
    ){
        Page<responsable> responsablePage= responsableRepository.findAllByNomContains(key, PageRequest.of(page,size));
        model.addAttribute("pages",responsablePage.getContent());
        model.addAttribute("nbrPages",new int[responsablePage.getTotalPages()]);
        model.addAttribute("key",key);
        model.addAttribute("pageCurrent",page);
        return "home";
    }
    /*
    @GetMapping(value = "/admin/delete")
    public String delete(Long id,int page, String key){

        responsableRepository.deleteById(id);
        return "redirect:/admin/home?page="+page+ "&key=" +key;
    }
     */
    @GetMapping(value = "/admin/delete")
    public String delete(Long id, int page, String key) {

        responsable responsable = responsableRepository.findById(id).orElse(null);
        if (responsable != null) {
            // Delete user and associated roles
            serviceImpl.deleteUserAndRoles(responsable.getNom());

            // Delete responsable
            responsableRepository.delete(responsable);
        }

        return "redirect:/admin/home?page=" + page + "&key=" + key;
    }

   /* @GetMapping("/admin/add")
    public String add(Model model){
        model.addAttribute("responsable",new responsable());
        return "add";
    }
    // add responsable addUser addRoleToUser
   @GetMapping("/admin/add")
   public String add(Model model){
       model.addAttribute("responsable",new responsable());
       model.addAttribute("appUser", new appUser());
       model.addAttribute("appRole", new appRole());
       return "add";
   }
   */


   /* @PostMapping("/admin/save")
    public String saveEtu(Model model, @Valid responsable responsable, BindingResult bindingResult){
        if (bindingResult.hasErrors())return "add";
        responsableRepository.save(responsable);
        return "redirect:/admin/home";
    }

    */
   @GetMapping("/admin/add")
   public String add(Model model){
       model.addAttribute("responsable",new responsable());
       model.addAttribute("appUser",new appUser());
       model.addAttribute("roles", serviceImpl.getAllRoles());
       return "add";
   }
    @PostMapping("/admin/save")
    public String save(Model model, @Valid responsable responsable, BindingResult bindingResult,
                       @Valid appUser appUser, BindingResult userBindingResult){
        String roleName;
        if (bindingResult.hasErrors() || userBindingResult.hasErrors()){
            return "add";
        }

        responsableRepository.save(responsable);
        appUser.setUsername(responsable.getNom()); // set the username to the value of nom

        appUser.setPassword(appUser.getPasswordEncoder().encode(appUser.getPassword()));
        serviceImpl.addUser(appUser.getUsername(), appUser.getPassword(), appUser.getPassword());
     // serviceImpl.addRoleToUser(appUser.getUsername(), "PROFESSEUR");
            roleName = String.valueOf(responsable.getRole());
          serviceImpl.addRoleToUser(appUser.getUsername(),roleName );
        System.out.println("Role name: " + roleName);
        return "redirect:/admin/home";
    }

    @GetMapping("/admin/edit")
    public String edit(Model model, @RequestParam Long id) {
        responsable responsable = responsableRepository.findById(id).orElse(null);
        if (responsable == null) throw new RuntimeException("Responsable not found");

        // Load user information
        appUser appUser = serviceImpl.loadUserByUsername(responsable.getNom());
        if (appUser == null) throw new RuntimeException("User not found");

        // Add user information to model
        model.addAttribute("responsable", responsable);
        model.addAttribute("appUser", appUser);

        return "edit";
    }

    @PostMapping("/admin/edit")
    public String save(Model model, @Valid responsable responsable, BindingResult bindingResult,
                       @Valid appUser appUser, BindingResult userBindingResult,
                       @RequestParam(name = "page",defaultValue = "0") int page,
                       @RequestParam(name = "key",defaultValue = "") String key) {
        if (bindingResult.hasErrors() || userBindingResult.hasErrors()) {
            return "edit";
        }

        responsableRepository.save(responsable);
        appUser.setUsername(responsable.getNom());

        appUser.setPassword(appUser.getPasswordEncoder().encode(appUser.getPassword()));
        serviceImpl.editUser(appUser.getUsername(), appUser.getPassword(), appUser.getPassword());
        String roleName;
        roleName = String.valueOf(responsable.getRole());
        serviceImpl.addRoleToUser(appUser.getUsername(), roleName);

        return "redirect:/admin/home?page="+page+ "&key=" +key;
    }

    @GetMapping("/admin/edit-form")
    public String editForm(Model model, Long id, int page,String key) {
        // Fetch the responsable by id
        responsable responsable = responsableRepository.findById(id).orElse(null);
        if (responsable == null) throw new RuntimeException("Responsable not found");

        // Add the responsable and other necessary data to the model
        model.addAttribute("responsable", responsable);
        model.addAttribute("appUser", new appUser());
        model.addAttribute("roles", serviceImpl.getAllRoles());
        model.addAttribute("page", page);
        model.addAttribute("key", key);

        return "edit";
    }

    @PostMapping("/admin/saveEdit")
    public String saveEdit(Model model, @Valid responsable responsable, BindingResult bindingResult, @RequestParam(name = "page",defaultValue = "0") int page, @RequestParam(name = "key",defaultValue = "") String key){
        if(bindingResult.hasErrors())return "edit";
        responsableRepository.save(responsable);
        return "redirect:/admin/home?page="+page+ "&key=" +key;
    }

}
