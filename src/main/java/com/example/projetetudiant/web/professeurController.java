package com.example.projetetudiant.web;

import com.example.projetetudiant.dto.EtudiantDTO;
import com.example.projetetudiant.dto.FetchResponseDTO;
import com.example.projetetudiant.dto.MatiereDTO;
import com.example.projetetudiant.entities.*;
import com.example.projetetudiant.repositories.*;
import com.example.projetetudiant.security.services.ImplServices;
import com.example.projetetudiant.security.services.iservice;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
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
    private employeRepository employeRepository;
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

        // Retrieve the authentication of the currently logged-in user
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Retrieve the username of the currently logged-in user
        String username = authentication.getName();
        // Retrieve the employee (professeur) associated with the logged-in user
        employe professeur = employeRepository.findByNom(username);

        // Retrieve the specific list of classes associated with the professor
        List<classe> classes = classeRepository.findByEmployes(professeur);
        // Retrieve the specific list of departments associated with the professor
        List<departement> departements = departementRepository.findByEmployes(professeur);

        // Set the list of classes and departments as model attributes for the view
        model.addAttribute("classes", classes);
        model.addAttribute("departements", departements);
        // Check if class and department parameters are provided

        return "add-note";
    }
/*
    @GetMapping("/fetch-students")
    public ResponseEntity<List<etudiant>> fetchStudents(@RequestParam("classId") Long classId,
                                                        @RequestParam("departmentId") Long departmentId) {
        System.out.println("classId: " + classId); // Log the value of classId
        System.out.println("departmentId: " + departmentId); // Log the value of departmentId

        if (classId != null && departmentId != null) {
            // Retrieve the students (etudiants) based on the selected class and department
            classe selectedClass = classeRepository.findById(classId).orElse(null);
            departement selectedDepartement = departementRepository.findById(departmentId).orElse(null);
            if (selectedClass != null && selectedDepartement != null) {
                List<etudiant> etudiants = etudiantRepository.findByClasseAndDepartement(selectedClass, selectedDepartement);
                System.out.println("Students:");
                for (etudiant etudiant : etudiants) {
                    System.out.println("Student ID: " + etudiant.getId());
                    System.out.println("Student Name: " + etudiant.getNom());
                    // Print other relevant information as needed
                }
                return ResponseEntity.ok(etudiants);
            }
        }

        // If no students found or classId/departmentId is null, return an empty list
        return ResponseEntity.ok(Collections.emptyList());
    }


@GetMapping("/fetch-students")
public ResponseEntity<List<EtudiantDTO>> fetchStudents(@RequestParam("classId") Long classId,
                                                       @RequestParam("departmentId") Long departmentId) {
    System.out.println("classId: " + classId); // Log the value of classId
    System.out.println("departmentId: " + departmentId); // Log the value of departmentId

    if (classId != null && departmentId != null) {
        // Retrieve the students (etudiants) based on the selected class and department
        classe selectedClass = classeRepository.findById(classId).orElse(null);
        departement selectedDepartement = departementRepository.findById(departmentId).orElse(null);
        if (selectedClass != null && selectedDepartement != null) {
            List<etudiant> etudiants = etudiantRepository.findByClasseAndDepartement(selectedClass, selectedDepartement);
            System.out.println("Students:");
            for (etudiant etudiant : etudiants) {
                System.out.println("Student ID: " + etudiant.getId());
                System.out.println("Student Name: " + etudiant.getNom());
                // Print other relevant information as needed
            }

            List<EtudiantDTO> etudiantDTOs = new ArrayList<>();
            for (etudiant etudiant : etudiants) {
                EtudiantDTO etudiantDTO = new EtudiantDTO(etudiant.getId(), etudiant.getNom());
                // Set other relevant properties in the DTO
                etudiantDTOs.add(etudiantDTO);
            }

            return ResponseEntity.ok(etudiantDTOs);
        }
    }

    // If no students found or classId/departmentId is null, return an empty list
    return ResponseEntity.ok(Collections.emptyList());
}

 */
@GetMapping("/fetch-students")
public ResponseEntity<FetchResponseDTO> fetchStudents(@RequestParam("classId") Long classId,
                                                      @RequestParam("departmentId") Long departmentId) {
    System.out.println("classId: " + classId); // Log the value of classId
    System.out.println("departmentId: " + departmentId); // Log the value of departmentId

    if (classId != null && departmentId != null) {
        // Retrieve the students (etudiants) based on the selected class and department
        classe selectedClass = classeRepository.findById(classId).orElse(null);
        departement selectedDepartement = departementRepository.findById(departmentId).orElse(null);
        if (selectedClass != null && selectedDepartement != null) {
            List<etudiant> etudiants = etudiantRepository.findByClasseAndDepartement(selectedClass, selectedDepartement);
            System.out.println("Students:");
            for (etudiant etudiant : etudiants) {
                System.out.println("Student ID: " + etudiant.getId());
                System.out.println("Student Name: " + etudiant.getNom());
                // Print other relevant information as needed
            }

            List<EtudiantDTO> etudiantDTOs = new ArrayList<>();
            for (etudiant etudiant : etudiants) {
                EtudiantDTO etudiantDTO = new EtudiantDTO(etudiant.getId(), etudiant.getNom());
                // Set other relevant properties in the DTO
                etudiantDTOs.add(etudiantDTO);
            }

            // Retrieve the subjects (matieres) based on the selected class and department
            List<matiere> matieres = matiereRepository.findByClasse(selectedClass);
            System.out.println("Subjects:");
            for (matiere matiere : matieres) {
                System.out.println("Subject ID: " + matiere.getIdMat());
                System.out.println("Subject Name: " + matiere.getNom());
                // Print other relevant information as needed
            }

            List<MatiereDTO> matiereDTOs = new ArrayList<>();
            for (matiere matiere : matieres) {
                MatiereDTO matiereDTO = new MatiereDTO(matiere.getIdMat(), matiere.getNom());
                // Set other relevant properties in the DTO
                matiereDTOs.add(matiereDTO);
            }

            FetchResponseDTO responseDTO = new FetchResponseDTO(etudiantDTOs, matiereDTOs);
            return ResponseEntity.ok(responseDTO);
        }
    }

    // If no students found or classId/departmentId is null, return an empty response
    FetchResponseDTO emptyResponseDTO = new FetchResponseDTO(Collections.emptyList(), Collections.emptyList());
    return ResponseEntity.ok(emptyResponseDTO);
}


    @PostMapping("/ajouter-note")
    public String addNote(@RequestParam("etudiantId") Long etudiantId,
                          @RequestParam("matiereId") Long matiereId,
                          @RequestParam("Dc") double Dc,
                          @RequestParam("Ds") double Ds) {

        // Retrieve the student and subject from the database
        etudiant etudiant = etudiantRepository.findById(etudiantId).orElse(null);
        matiere matiere = matiereRepository.findById(matiereId).orElse(null);

        if (etudiant != null && matiere != null) {
            // Create a new note
            note note = new note();
            note.setEtudiant(etudiant);
            note.setMatiere(matiere);
            note.setDc(Dc);
            note.setDs(Ds);

            // Save the note in the database
            noteRepository.save(note);
        }

        return "redirect:/professeur/Liste-Note"; // Redirect to the desired page
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