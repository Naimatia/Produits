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
       /* http.formLogin();
        http.authorizeRequests().antMatchers("/").permitAll();
        http.authorizeRequests().antMatchers("/webjars/**").permitAll();
       // http.authorizeRequests().antMatchers("/admin/**").hasAuthority("ADMIN");
      //  http.authorizeRequests().antMatchers("/user/**").hasAuthority("USER");
        http.exceptionHandling().accessDeniedPage("/403");
        http.authorizeRequests().anyRequest().authenticated();
        http.authorizeRequests().antMatchers("/css/**", "/js/**").permitAll();



        */
            http.authorizeRequests()
                    .antMatchers("/").permitAll()
                    .antMatchers("/SignEtudiant").permitAll()
                    .antMatchers("/SignUser").permitAll()
                   // .antMatchers("/InterfaceEtudiant").permitAll()
                    .antMatchers("/webjars/**").permitAll()
                    .antMatchers("/css/**", "/js/**").permitAll()
                    .antMatchers("/image/**").permitAll() // add this line for image files
                    .anyRequest().authenticated();
           http.formLogin()
                .loginPage("/SignUser")
                   .defaultSuccessUrl("/user/home")
                   .permitAll()
                   .and()
           .logout()
                   .logoutUrl("/logout")
                   .logoutSuccessUrl("/logout")
                   .invalidateHttpSession(true)
                   .permitAll();
      /*  http.formLogin()
                .loginPage("/SignEtudiant")
                .defaultSuccessUrl("/InterfaceEtudiant")
                .permitAll()
                .and();
            http.exceptionHandling().accessDeniedPage("/403");
*/
    }
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("user").password("{noop}password").roles("USER"); // add a test user
    }
}
