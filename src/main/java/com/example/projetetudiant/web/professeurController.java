package com.example.projetetudiant.web;

import com.example.projetetudiant.entities.etudiant;
import com.example.projetetudiant.repositories.ProfesseurRepository;
import com.example.projetetudiant.repositories.classeRepository;
import com.example.projetetudiant.repositories.departementRepository;
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
public class professeurController {
    private ProfesseurRepository professeurRepository;
    private  departementRepository departementRepository;
    private classeRepository classeRepository;
    @GetMapping("/professeur/gestionEtudiant")
    public String gestionEtudiant(Model model, @RequestParam(name = "page",defaultValue = "0") int page,
                                      @RequestParam(name = "size",defaultValue = "6") int size,
                                      @RequestParam(name = "key",defaultValue = "") String key
    ){
        Page<etudiant> etudiantPages=professeurRepository.findAllByNomContains(key,PageRequest.of(page,size));
        model.addAttribute("pages",etudiantPages.getContent());
        model.addAttribute("nbrPages",new int[etudiantPages.getTotalPages()]);
        model.addAttribute("key",key);
        model.addAttribute("pageCurrent",page);
        return "gestionEtudiant";
    }


    @GetMapping("/professeur/InterfaceProfesseur")
    public String InterfaceProfesseur(Model model){
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

    @GetMapping("/professeur/editEtudiant")
    public  String editEtudiant(Model model, Long id, int page,String key){
       etudiant etudiant=professeurRepository.findById(id).orElse(null);
      //  model.addAttribute("selectedDepartement",etudiant.getDepartement().getIddep());
        model.addAttribute("departements", departementRepository.findAll());
        model.addAttribute("classes", classeRepository.findAll());
        model.addAttribute("etudiant",etudiant);
        model.addAttribute("page",page);
        model.addAttribute("key",key);

        return "editEtudiant";
    }

    @PostMapping("/professeur/editEtudiant")
    public String saveEditEtudiant(Model model, @Valid etudiant etudiant, BindingResult bindingResult, @RequestParam(name = "page",defaultValue = "0") int page, @RequestParam(name = "key",defaultValue = "") String key){
        if(bindingResult.hasErrors())return "saveEditEtudiant";
        professeurRepository.save(etudiant);
        return "redirect:/professeur/gestionEtudiant?page="+page+ "&key=" +key;
    }


  /*  @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        // perform logout actions, e.g., invalidate session
        request.getSession().invalidate();
        // redirect to login page
        return "redirect:/SignEmploye";
    }

   */
}