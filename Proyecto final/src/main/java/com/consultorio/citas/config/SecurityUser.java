package com.consultorio.citas.config;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.consultorio.citas.model.usuarioModel;

public class SecurityUser  implements UserDetails{
    
    private usuarioModel user;
/* Se cra una instancia de la clase usuario */
     public SecurityUser(usuarioModel user) {
        super();
        this.user = user;
    }

  
/*
 *Se obtiene la autorizaciones para los usuarios 
 */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(user
        .getRole()
        .split(","))
        .map(SimpleGrantedAuthority::new)
        .toList();
    }

    @Override
    public String getPassword() {
        return user.getContrasenna();
    }

    @Override
    public String getUsername() {
        return user.getCedula();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
