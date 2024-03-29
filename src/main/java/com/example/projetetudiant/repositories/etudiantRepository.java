package com.example.projetetudiant.repositories;

import com.example.projetetudiant.entities.classe;
import com.example.projetetudiant.entities.departement;
import com.example.projetetudiant.entities.etudiant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface etudiantRepository extends JpaRepository<etudiant,Long> {

    Page<etudiant>  findAllByNomContains(String key, Pageable pageable);
    etudiant findByNom(String nom);


    List<etudiant> findByClasse(classe classe);

    List<etudiant> findByClasseAndDepartement(classe selectedClasse, departement selectedDepartement);


}
