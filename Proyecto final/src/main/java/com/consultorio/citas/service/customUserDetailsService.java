package com.consultorio.citas.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.consultorio.citas.config.SecurityUser;
import com.consultorio.citas.repository.usuarioRepository;

@Service
public class customUserDetailsService implements UserDetailsService {
    @Autowired
    private final usuarioRepository userRepository;

    public customUserDetailsService(usuarioRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
   /*
    * Valida se el usuario existe, en caso de no existir despliega una excepción y no permite el ingreso
    */
        return userRepository
                .findByCedula(username)
                .map(SecurityUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found: " + username));
    }
}
