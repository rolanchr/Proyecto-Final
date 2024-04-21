package com.consultorio.citas.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.consultorio.citas.model.usuarioModel;


public interface usuarioRepository extends  CrudRepository<usuarioModel, String> {
/*
 * Busca los datos del usuario por cedula
 * 
 */
    Optional<usuarioModel>   findByCedula(String cedula);

}
