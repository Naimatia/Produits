package com.example.projetetudiant.repositories;

import com.example.projetetudiant.entities.matiere;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface matiereRepository extends JpaRepository<matiere,Long> {

    Page<matiere> findAllByNomContains(String key, Pageable pageable);
}