package com.consultorio.citas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.consultorio.citas.model.usuarioModel;
import com.consultorio.citas.repository.usuarioRepository;

/*
 * Clase para insertar datos antes de iniciar el programa. 
 * Esta clase inicializa valores en la base de datos
 * 
 */
@Component
public class DataLoader {
    @Autowired
    private usuarioRepository repository;

    public DataLoader(usuarioRepository repository) {
        this.repository = repository;
        LoadUsers();
    }

    private void LoadUsers() {
        repository.save(new usuarioModel("101110111", "Admin", "Admin", "Activo", "ADMIN",
                "$2a$10$xnZ8wf98wfUX7ewJA6oW0uZvOw3R8CTTVXwcUo5N3s0RfRavyDw0W",
                "admin@admin.com"));
                repository.save(new usuarioModel("202220222", "Jose Marcos", "Soto Jara", "Activo", "USER",
                        "$2a$10$xnZ8wf98wfUX7ewJA6oW0uZvOw3R8CTTVXwcUo5N3s0RfRavyDw0W",
                        "user@user.com"));
    }

}
