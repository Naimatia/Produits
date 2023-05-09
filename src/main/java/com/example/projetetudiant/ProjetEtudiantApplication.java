package com.example.projetetudiant;

import com.example.projetetudiant.EnumType.role;
import com.example.projetetudiant.entities.departement;
import com.example.projetetudiant.entities.employe;
import com.example.projetetudiant.entities.matiere;
import com.example.projetetudiant.repositories.departementRepository;
import com.example.projetetudiant.repositories.employeRepository;
import com.example.projetetudiant.repositories.matiereRepository;
import com.example.projetetudiant.security.services.iservice;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.stream.Stream;

@SpringBootApplication
public class ProjetEtudiantApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjetEtudiantApplication.class, args);
    }

  //   @Bean
    CommandLineRunner start3(employeRepository employeRepository){
        return  args -> {

            Stream.of("mohammed","ahmed","kamal","farah","najat","fouad").forEach(name->{
                employe r=new employe();
                r.setNom(name);
                r.setPrenom("bentest");
                r.setEmail(name+"@gmail.com");
                r.setRole(Math.random()<0.5?role.PROFESSEUR:role.CHEFDEP);
                r.setDateDebutTravail(new Date());
                employeRepository.save(r);
            });
        };
    }
 //   @Bean
    CommandLineRunner start3(departementRepository departementRepository) {
        return args -> {
            Stream.of("informatique", "éléctrique", "mécanique").forEach(name -> {
                departement d = new departement();
                d.setNom(name);
                departementRepository.save(d);
            });
        };
    }
   // @Bean
    CommandLineRunner start4(matiereRepository matiereRepository) {
        return args -> {
            Stream.of("Mathématique", "informatique", "Français").forEach(name -> {
                matiere m = new matiere();
                m.setNom(name);
                matiereRepository.save(m);
            });
        };
    }



     //   @Bean
    CommandLineRunner start1(iservice serviceImpl){
        return args -> {
            serviceImpl.addUser("aziz","1234","1234");
          serviceImpl.addUser("hamza","","");
         serviceImpl.addRole("ADMIN","");
        serviceImpl.addRole("CHEFDEP","");
        serviceImpl.addRole("PROFESSEUR","");

          serviceImpl.addRoleToUser("aziz","PROFESSEUR");
         serviceImpl.addRoleToUser("hamza","ADMIN");

        } ;
    }

    @Bean
   PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
   }

}
