package com.example.projetetudiant.repositories;

import com.example.projetetudiant.entities.employe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface employeRepository extends JpaRepository<employe,Long> {
    Page<employe> findAllByNomContains(String key, Pageable pageable);

}
