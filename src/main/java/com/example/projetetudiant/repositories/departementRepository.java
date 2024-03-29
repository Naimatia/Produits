package com.example.projetetudiant.repositories;

import com.example.projetetudiant.entities.departement;
import com.example.projetetudiant.entities.employe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface departementRepository extends JpaRepository<departement,Long>{

        Page<departement> findAllByNomContains(String key, Pageable pageable);

    List<departement> findByEmployes(employe emp);

}
