package com.example.projetetudiant.dto;

import java.util.List;

public class FetchResponseDTO {
    private List<EtudiantDTO> etudiantDTOs;
    private List<MatiereDTO> matiereDTOs;

    public FetchResponseDTO(List<EtudiantDTO> etudiantDTOs, List<MatiereDTO> matiereDTOs) {
        this.etudiantDTOs = etudiantDTOs;
        this.matiereDTOs = matiereDTOs;
    }

    public List<EtudiantDTO> getEtudiantDTOs() {
        return etudiantDTOs;
    }

    public void setEtudiantDTOs(List<EtudiantDTO> etudiantDTOs) {
        this.etudiantDTOs = etudiantDTOs;
    }

    public List<MatiereDTO> getMatiereDTOs() {
        return matiereDTOs;
    }

    public void setMatiereDTOs(List<MatiereDTO> matiereDTOs) {
        this.matiereDTOs = matiereDTOs;
    }
}
