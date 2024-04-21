package com.consultorio.citas.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class usuarioModel {
    @Id
    private String cedula;
    private String nombre;
    private String apellidos;
    private String estado="Activo"; /*1 = usuario activo | 0 = Usuario inactivo */
    private String role="USER"; /*USER = Paciente | ADMIN = Administrador */
    private String contrasenna;
    private String correo;
    
    public usuarioModel() {
    }
    public usuarioModel(String cedula, String nombre, String apellidos, String estado, String role, String contrasenna,String correo) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.estado = estado;
        this.role = role;
        this.contrasenna = contrasenna;
        this.correo = correo;
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
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public String getContrasenna() {
        return contrasenna;
    }
    public void setContrasenna(String contrasenna) {
        this.contrasenna = contrasenna;
    }
    public String getCorreo() {
        return correo;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
