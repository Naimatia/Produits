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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@AllArgsConstructor
public class etudiantController {
    private etudiantRepository etudiantRepository;
    private iservice serviceImpl;
    // @GetMapping("/user/home")
    public String home(Model model, @RequestParam(name = "page",defaultValue = "0") int page,
                       @RequestParam(name = "size",defaultValue = "6") int size,
                       @RequestParam(name = "key",defaultValue = "") String key
    ){
        Page<etudiant> etudiantPages=etudiantRepository.findAllByNomContains(key,PageRequest.of(page,size));
        model.addAttribute("pages",etudiantPages.getContent());
        model.addAttribute("nbrPages",new int[etudiantPages.getTotalPages()]);
        model.addAttribute("key",key);
        model.addAttribute("pageCurrent",page);
        return "home";
    }
    // @GetMapping(value = "/admin/delete")
    public String delete(Long id,int page, String key){

        etudiantRepository.deleteById(id);
        return "redirect:/user/home?page="+page+ "&key=" +key;
    }

    // @GetMapping("/admin/add")
    public String add(Model model){
        model.addAttribute("etudiant",new etudiant());
        return "add";
    }


    // @PostMapping("/admin/save")
    public String saveEtu(Model model, @Valid etudiant etudiant, BindingResult bindingResult){
        if (bindingResult.hasErrors())return "add";
        etudiantRepository.save(etudiant);
        return "redirect:/user/home";
    }

    //@GetMapping("/admin/edit")
    public  String edit(Model model, Long id, int page,String key){
        etudiant etudiant=etudiantRepository.findById(id).orElse(null);
        model.addAttribute("etudiant",etudiant);
        model.addAttribute("page",page);
        model.addAttribute("key",key);
        return "edit";
    }

    //   @PostMapping("/admin/saveEdit")
    public String saveEdit(Model model, @Valid etudiant etudiant, BindingResult bindingResult, @RequestParam(name = "page",defaultValue = "0") int page, @RequestParam(name = "key",defaultValue = "") String key){
        if(bindingResult.hasErrors())return "edit";
        etudiantRepository.save(etudiant);
        return "redirect:/user/home?page="+page+ "&key=" +key;
    }

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
        return "InterfaceEtudiant.html";
    }


}