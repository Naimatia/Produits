package com.example.projetetudiant.security;

import com.example.projetetudiant.security.services.userDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {


    private userDetailsService userDetailsService;

    @Configuration
    @Order(1)
    public static class FormLoginConfigurer extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.requestMatchers()
                    .antMatchers("/SignUser", "/signin", "/authenticate")
                    .and()
                    .formLogin()
                    .loginPage("/SignUser")
                    .defaultSuccessUrl("/admin/home")
                  //  .permitAll()
                    .and()
                    .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/")
                    .and();

        }
    }

    @Configuration
    @Order(2)
    public static class ProfesseurFormLoginConfigurer extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.requestMatchers()
                    .antMatchers("/SignEmploye")
                    .and()
                    .formLogin()
                    .loginPage("/SignEmploye")
                    .defaultSuccessUrl("/professeur/gestionEtudiant")
                    .and()
                    .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/")
                    .and();


        }
    }
    @Configuration
    @Order(3)
    public static class EtudiantFormLoginConfigurer extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.requestMatchers()
                    .antMatchers("/SignEtudiant")
                    .and()
                    .formLogin()
                    .loginPage("/SignEtudiant")
                    .defaultSuccessUrl("/InterfaceEtudiant")
                    .and()
                    .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/")
                    .and();


        }
    }
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }
}
