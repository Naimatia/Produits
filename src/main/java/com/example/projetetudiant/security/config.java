package com.example.projetetudiant.security;

import com.example.projetetudiant.security.services.userDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class config extends WebSecurityConfigurerAdapter {
    private userDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/","/gestionEtudiant", "/SignEtudiant", "/SignEmploye", "/SignUser", "/webjars/**", "/css/**", "/js/**", "/image/**").permitAll()
                .antMatchers("/admin/**").hasAuthority("ADMIN")
                .antMatchers("/professeur/**").hasAnyAuthority("PROFESSEUR","CHEFDEP")
                .antMatchers("/error").permitAll()
                .antMatchers("/403").permitAll()
                .anyRequest().authenticated();
        http.exceptionHandling().accessDeniedPage("/403");


    }
}
