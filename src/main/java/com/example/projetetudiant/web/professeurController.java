package com.example.projetetudiant.web;

import com.example.projetetudiant.entities.etudiant;
import com.example.projetetudiant.repositories.ProfesseurRepository;
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
public class professeurController {
    private ProfesseurRepository professeurRepository;

    @GetMapping("/professeur/InterfaceProfesseur")
    public String homeProfesseur(Model model, @RequestParam(name = "page",defaultValue = "0") int page,
                       @RequestParam(name = "size",defaultValue = "6") int size,
                       @RequestParam(name = "key",defaultValue = "") String key
    ){
        Page<etudiant> etudiantPages=professeurRepository.findAllByNomContains(key,PageRequest.of(page,size));
        model.addAttribute("pages",etudiantPages.getContent());
        model.addAttribute("nbrPages",new int[etudiantPages.getTotalPages()]);
        model.addAttribute("key",key);
        model.addAttribute("pageCurrent",page);
        return "InterfaceProfesseur";
    }
    //@GetMapping(value = "/professeur/delete")
    public String delete(Long id,int page, String key){

        professeurRepository.deleteById(id);
        return "redirect:/professeur/InterfaceProfesseur?page="+page+ "&key=" +key;
    }

    //@GetMapping("/admin/add")
    public String add(Model model){
        model.addAttribute("etudiant",new etudiant());
        return "add";
    }


    // @PostMapping("/admin/save")
    public String saveEtu(Model model, @Valid etudiant etudiant, BindingResult bindingResult){
        if (bindingResult.hasErrors())return "add";
        professeurRepository.save(etudiant);
        return "redirect:/user/home";
    }

    //@GetMapping("/admin/edit")
    public  String edit(Model model, Long id, int page,String key){
        etudiant etudiant=professeurRepository.findById(id).orElse(null);
        model.addAttribute("etudiant",etudiant);
        model.addAttribute("page",page);
        model.addAttribute("key",key);
        return "edit";
    }

    //   @PostMapping("/admin/saveEdit")
    public String saveEdit(Model model, @Valid etudiant etudiant, BindingResult bindingResult, @RequestParam(name = "page",defaultValue = "0") int page, @RequestParam(name = "key",defaultValue = "") String key){
        if(bindingResult.hasErrors())return "edit";
        professeurRepository.save(etudiant);
        return "redirect:/user/home?page="+page+ "&key=" +key;
    }

    @GetMapping("/SignResponsable")
    public String SignResponsable(Model model){
        return "SignResponsable.html";
    }
    //@RequestMapping("/SignResponsable")

}