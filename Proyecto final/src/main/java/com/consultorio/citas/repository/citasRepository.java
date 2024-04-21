package com.consultorio.citas.repository;



import org.springframework.data.repository.CrudRepository;

import com.consultorio.citas.model.citasModel;

public interface citasRepository extends CrudRepository<citasModel, Long>{
/*
 * Metodo para buscar la cita por paciente
 */
    Iterable<citasModel>   findByUsuario(String usuario);
    Iterable<citasModel>   findByEstado(String estado);
}
