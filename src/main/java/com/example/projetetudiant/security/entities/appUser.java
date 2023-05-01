package com.example.projetetudiant.security.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class appUser {
    @Id
    private String Id;
    @Column(unique = false)
    private String username;
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<appRole> listRoles=new ArrayList<>();
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
