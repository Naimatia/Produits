package com.example.projetetudiant;

import com.example.projetetudiant.EnumType.genre;
import com.example.projetetudiant.EnumType.role;
import com.example.projetetudiant.entities.etudiant;
import com.example.projetetudiant.entities.responsable;
import com.example.projetetudiant.repositories.etudiantRepository;
import com.example.projetetudiant.repositories.responsableRepository;
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
    CommandLineRunner start3(responsableRepository responsableRepository){
        return  args -> {

            Stream.of("mohammed","ahmed","kamal","farah","najat","fouad").forEach(name->{
                responsable r=new responsable();
                r.setNom(name);
                r.setPrenom("bentest");
                r.setEmail(name+"@gmail.com");
                r.setRole(Math.random()<0.5?role.PROFESSEUR:role.CHEFDEP);
                r.setDateDebutTravail(new Date());
                responsableRepository.save(r);
            });
        };
    }

  @Bean
    CommandLineRunner start1(iservice serviceImpl){
        return args -> {
            serviceImpl.addUser("naim","1234","1234");
          serviceImpl.addUser("hamza","0000","0000");
          serviceImpl.addRole("ADMIN","");
          serviceImpl.addRole("CHEFDEP","");
          serviceImpl.addRole("PROFESSEUR","");

            serviceImpl.addRoleToUser("naim","PROFESSEUR");
         serviceImpl.addRoleToUser("hamza","ADMIN");

        } ;
    }

    @Bean
   PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
   }

}
