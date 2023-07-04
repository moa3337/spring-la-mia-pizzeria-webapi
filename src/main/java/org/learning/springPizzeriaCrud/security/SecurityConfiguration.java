package org.learning.springPizzeriaCrud.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {
    /* Per definire un AuthenticationProvider ho bisogno di:
    - UserDetailService
    - PasswordEncoder
     */

    // Questo lo UserDetailService
    @Bean
    DatabaseUserDetailServices userDetailsService() {
        return new DatabaseUserDetailServices();
    }

    // Questo è il PasswordEncoder
    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        // Creo l'authenticationProvider
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        // Gli setto il PasswordEncoder
        provider.setPasswordEncoder(passwordEncoder());
        // Gli setto UserDetailServices
        provider.setUserDetailsService(userDetailsService());
        return provider;
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Definisco la catena di filtri
        http.authorizeHttpRequests()
                .requestMatchers("/ingredients").hasAnyAuthority("ADMIN")
                .requestMatchers("/pizzas/edit/**").hasAnyAuthority("ADMIN")
                .requestMatchers("/pizzas/create").hasAnyAuthority("ADMIN")
                .requestMatchers("/pizzas/**").hasAnyAuthority("ADMIN", "USER")
                .requestMatchers("/ingredients/**").hasAnyAuthority("ADMIN")
                .requestMatchers(HttpMethod.POST, "/ingredients/**").hasAnyAuthority("ADMIN")
                .requestMatchers("/**").permitAll()
                .and().formLogin()
                .and().logout();
        // Disabilitazione csrf per poter invocare le api da Postman
        http.csrf().disable();
        return http.build();
    }
}
