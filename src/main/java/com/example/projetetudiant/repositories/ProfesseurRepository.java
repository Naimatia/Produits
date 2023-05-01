package com.example.projetetudiant.repositories;

import com.example.projetetudiant.entities.professeur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfesseurRepository extends JpaRepository<professeur, Long> {
    Page<professeur> findAllByNomContains(String key, Pageable pageable);
}
