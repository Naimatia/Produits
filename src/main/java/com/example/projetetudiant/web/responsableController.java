package com.example.projetetudiant.web;

import com.example.projetetudiant.entities.etudiant;
import com.example.projetetudiant.entities.responsable;
import com.example.projetetudiant.repositories.responsableRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
public class responsableController {
    private final responsableRepository responsableRepository;

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
    @GetMapping(value = "/admin/delete")
    public String delete(Long id,int page, String key){

        responsableRepository.deleteById(id);
        return "redirect:/admin/home?page="+page+ "&key=" +key;
    }

    @GetMapping("/admin/add")
    public String add(Model model){
        model.addAttribute("responsable",new responsable());
        return "add";
    }


    @PostMapping("/admin/save")
    public String saveEtu(Model model, @Valid responsable responsable, BindingResult bindingResult){
        if (bindingResult.hasErrors())return "add";
        responsableRepository.save(responsable);
        return "redirect:/admin/home";
    }

    @GetMapping("/admin/edit")
    public  String edit(Model model, Long id, int page,String key){
        responsable responsable=responsableRepository.findById(id).orElse(null);
        model.addAttribute("responsable",responsable);
        model.addAttribute("page",page);
        model.addAttribute("key",key);
        return "edit";
    }

    @PostMapping("/admin/saveEdit")
    public String saveEdit(Model model, @Valid responsable responsable, BindingResult bindingResult, @RequestParam(name = "page",defaultValue = "0") int page, @RequestParam(name = "key",defaultValue = "") String key){
        if(bindingResult.hasErrors())return "edit";
        responsableRepository.save(responsable);
        return "redirect:/admin/home?page="+page+ "&key=" +key;
    }
}
