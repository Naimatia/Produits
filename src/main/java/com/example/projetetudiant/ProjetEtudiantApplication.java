package com.example.projetetudiant;

import com.example.projetetudiant.EnumType.role;
import com.example.projetetudiant.entities.classe;
import com.example.projetetudiant.entities.departement;
import com.example.projetetudiant.entities.etudiant;
import com.example.projetetudiant.entities.matiere;
import com.example.projetetudiant.repositories.classeRepository;
import com.example.projetetudiant.repositories.departementRepository;
import com.example.projetetudiant.repositories.etudiantRepository;
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
/*
     @Bean
    CommandLineRunner start3(etudiantRepository etudiantRepository){
        return  args -> {

            Stream.of("mohammed","ahmed","kamal","farah","najat","fouad").forEach(name->{
                etudiant r=new etudiant();
                r.setNom(name);
                r.setPrenom("bentest");
                r.setEmail(name+"@gmail.com");
                etudiantRepository.save(r);
            });
        };
    }

 @Bean
    CommandLineRunner start1(iservice serviceImpl){
        return args -> {
            serviceImpl.addUser("aziz","1234","1234");
          serviceImpl.addUser("hamza","","");
         serviceImpl.addRole("ADMIN","");
        serviceImpl.addRole("CHEFDEP","");
        serviceImpl.addRole("PROFESSEUR","");
            serviceImpl.addRole("ETUDIANT","");
            serviceImpl.addRoleToUser("aziz","PROFESSEUR");
         serviceImpl.addRoleToUser("hamza","ADMIN");

        } ;
    }
       @Bean
    CommandLineRunner start5(departementRepository departementRepository) {
        return args -> {
            Stream.of("informatique", "éléctrique", "mécanique").forEach(name -> {
                departement d = new departement();
                d.setNom(name);
                departementRepository.save(d);
            });
        };
    }
     @Bean
    CommandLineRunner start4(matiereRepository matiereRepository) {
        return args -> {
            Stream.of("Mathématique", "informatique", "Français").forEach(name -> {
                matiere m = new matiere();
                m.setNom(name);
                matiereRepository.save(m);
            });
        };
    }
    @Bean
    CommandLineRunner start6(classeRepository classeRepository) {
        return args -> {
            Stream.of("DSI21", "DSI22", "DSI23").forEach(name -> {
                classe c = new classe();
                c.setNom(name);
                classeRepository.save(c);
            });
        };
    }

 */
    @Bean
   PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
   }



}
