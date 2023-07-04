package org.learning.springPizzeriaCrud.security;

import org.learning.springPizzeriaCrud.model.User;
import org.learning.springPizzeriaCrud.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DatabaseUserDetailServices implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Recuperare user da db e partire dalla stringa Username
        Optional<User> result = userRepository.findByEmail(username);
        if (result.isPresent()) {
            // Costruisco uno userDetail a partire da user
            return new DatabaseUserDetails(result.get());
        } else {
            // Se non trovo l'utente con quella email lancio un eccezione
            throw new UsernameNotFoundException("User whit email " + username + " not found");
        }
    }
}
