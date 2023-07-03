package org.learning.springPizzeriaCrud.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class DatabaseUserDetailServices implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Recuperare user da db e partire dalla stringa Username

        // Costruire UserDetails a partire da quello user
        return null;
    }
}
