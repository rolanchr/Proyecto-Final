package com.consultorio.citas.config;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
@ControllerAdvice
public class MvcAdvice  {
     // variables globales para todos los htmls que están en template
     @ModelAttribute("isAdmin")
     public boolean test() {
        
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        System.out.println(this.getClass() + "-------Datos ____" + authentication.getDetails());
        System.out.println(this.getClass() + "------Autenticado ____" + authentication.isAuthenticated());
        System.out.println(this.getClass() + "------Nombre ____" + authentication.getName());
        boolean isAdmin = authentication.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ADMIN")); 
         return isAdmin;
     }
}
