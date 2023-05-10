package com.example.projetetudiant.entities;

import com.example.projetetudiant.EnumType.genre;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class etudiant {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    @Size(min = 2,max = 8)
    private String nom;
    @Size(min = 2,max = 10)
    private String prenom;
    @Email
    private String email;
    private String password;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateNiassance;
    @Enumerated(EnumType.STRING)
    private genre genre;
    @ManyToOne
    private departement departement;
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    public String toString() {
        return "etudiant{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", dateNiassance=" + dateNiassance +
                ", genre=" + genre +
                ", departement=" + departement +
                '}';
    }
}
