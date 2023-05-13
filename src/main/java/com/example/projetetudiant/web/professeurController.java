package com.example.projetetudiant.web;

import com.example.projetetudiant.entities.etudiant;
import com.example.projetetudiant.entities.matiere;
import com.example.projetetudiant.entities.note;
import com.example.projetetudiant.repositories.noteRepository;
import com.example.projetetudiant.repositories.ProfesseurRepository;
import com.example.projetetudiant.repositories.etudiantRepository;
import com.example.projetetudiant.repositories.matiereRepository;
import com.example.projetetudiant.repositories.classeRepository;
import com.example.projetetudiant.repositories.departementRepository;
import com.example.projetetudiant.security.services.ImplServices;
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
import java.util.List;

@Controller
@AllArgsConstructor
public class professeurController {
    private ProfesseurRepository professeurRepository;
    private etudiantRepository etudiantRepository;
    private matiereRepository matiereRepository;
    private noteRepository noteRepository;
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




    @GetMapping(value = "/professeur/delete")
    public String delete(Long id,int page, String key){

        professeurRepository.deleteById(id);
        return "redirect:/professeur/InterfaceProfesseur?page="+page+ "&key=" +key;
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
    @GetMapping("/ajouter-note")
    public String showAddNoteForm(Model model) {
        // Charger les étudiants et les matières depuis la base de données
        List<etudiant> etudiants = etudiantRepository.findAll();
        List<matiere> matieres = matiereRepository.findAll();

        // Passer les étudiants et les matières au modèle
        model.addAttribute("etudiants", etudiants);
        model.addAttribute("matieres", matieres);

        return "add-note.html"; // Retourne le nom du template (add-note.html)
    }

    // Traite la soumission du formulaire d'ajout de notes
    @PostMapping("/ajouter-note")
    public String addNote(@RequestParam("etudiantId") Long etudiantId,
                          @RequestParam("matiereId") Long matiereId,
                          @RequestParam("Dc") double Dc,
                          @RequestParam("Ds") double Ds) {
        // Récupérer l'étudiant et la matière depuis la base de données
        etudiant etudiant = etudiantRepository.findById(etudiantId).orElse(null);
        matiere matiere = matiereRepository.findById(matiereId).orElse(null);

        if (etudiant != null && matiere != null) {
            // Créer une nouvelle note
            note note = new note();
            note.setEtudiant(etudiant);
            note.setMatiere(matiere);
            note.setDc(Dc);
            note.setDs(Ds);


            // Enregistrer la note dans la base de données
            noteRepository.save(note);
        }

        return "redirect:/professeur/ListeNote"; // Redirige vers la page du formulaire
    }
    @GetMapping("/professeur/Liste-Note")
    public String afficherNotes(Model model) {
        List<note> listeNotes = noteRepository.findAll();
        model.addAttribute("listeNotes", listeNotes);
        return "Liste-Note";
    }

    @GetMapping("/professeur/ListeNote")
    public String ListeNote(Model model){
        return "Liste-Note.html";
    }

}