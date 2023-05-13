package com.example.projetetudiant.repositories;

import com.example.projetetudiant.entities.classe;
import com.example.projetetudiant.entities.etudiant;
import com.example.projetetudiant.entities.note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface noteRepository extends JpaRepository<note,Long> {
    List<note> findByEtudiantId(long id);
}
