package com.example.projetetudiant.repositories;

import com.example.projetetudiant.entities.responsable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface responsableRepository extends JpaRepository<responsable,Long> {
    Page<responsable> findAllByNomContains(String key, Pageable pageable);

}
