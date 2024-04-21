package com.consultorio.citas.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.consultorio.citas.model.medicoModel;


public interface medicoRepository extends  CrudRepository<medicoModel, String> {
/*
 * Busca toda la información de medico por cedula
 * 
 */
    Optional<medicoModel>   findByCedula(String cedula);

}
