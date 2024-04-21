package com.consultorio.citas.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "medico")
public class medicoModel {
    @Id
    private String cedula;
    private String nombre;
    private String apellidos;
    private String especialidad;
     public String getEspecialidad() {
        return especialidad;
    }
    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
    private String estado="Activo"; /*1 = usuario activo | 0 = Usuario inactivo */
    
    public medicoModel() {
    }
    public medicoModel(String cedula, String nombre, String apellidos, String estado, String especialidad) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.estado = estado;
        this.especialidad = especialidad;
    }
    /*
     * Getter y Setter
     * 
    */
    public String getCedula() {
        return cedula;
    }
    public void setCedula(String cedula) {
        this.cedula = cedula;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getApellidos() {
        return apellidos;
    }
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }

}
