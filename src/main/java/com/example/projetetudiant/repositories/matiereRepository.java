package com.example.projetetudiant.repositories;

import com.example.projetetudiant.entities.classe;
import com.example.projetetudiant.entities.departement;
import com.example.projetetudiant.entities.matiere;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface matiereRepository extends JpaRepository<matiere,Long> {

    Page<matiere> findAllByNomContains(String key, Pageable pageable);

}
