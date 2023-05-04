package com.example.projetetudiant.repositories;

import com.example.projetetudiant.entities.etudiant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface ProfesseurRepository extends JpaRepository<etudiant, Long> {
    Page<etudiant> findAllByNomContains(String key, Pageable pageable);
}
