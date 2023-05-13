package com.example.projetetudiant.repositories;

import com.example.projetetudiant.entities.classe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface classeRepository extends JpaRepository<classe,Long> {

    Page<classe> findAllByNomContains(String key, Pageable pageable);
    classe findByNom(String nom);
}

