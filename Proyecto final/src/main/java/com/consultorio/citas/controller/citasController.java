package com.consultorio.citas.controller;


import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.consultorio.citas.model.citasModel;
import com.consultorio.citas.repository.citasRepository;
import com.consultorio.citas.model.medicoModel;
import com.consultorio.citas.repository.medicoRepository;

@Controller
@RequestMapping("/citas")
public class citasController {
    /*
     * Se instancia el repositorio
     */
    @Autowired
    private citasRepository repository;

    @Autowired
    private medicoRepository medicoRepository; //Instancia de la clase medicoRepository, esto para obtener las especialidades

    @GetMapping("/nuevo")//Formulario para crear una nueva cita
    public String registrarse(Model model) {
        Iterable<medicoModel> medicos = medicoRepository.findAll();// obtiene la información de de los medicos
        model.addAttribute("cita", new citasModel());
        model.addAttribute("medicos", medicos);
        return "citas/nueva";
    }
/*
 * Lista las citas de todos los usuario. Esto es para los que tienen role de admin
 */
    @GetMapping("/")
    public String listarCitas(Model model) {
        try {// Manejo de errorres
            Iterable<citasModel> citas = repository.findAll();
            model.addAttribute("citas", citas);
            Iterable<citasModel> Pcitas = repository.findByEstado("Pendiente");
            model.addAttribute("Pcitas", Pcitas);
            Iterable<citasModel> Ccitas = repository.findByEstado("Cancelada");
            model.addAttribute("Ccitas", Ccitas);
            return "citas/index";
        } catch (Exception e) {
            System.out.println(e.toString());
            return "theme/error";
        }
    }
/*
 * Lista las citas pertenecientes al usuario. Esto es para los que tienen role de USER
 */
    @GetMapping("/micita")
    public String listarCitasPorUsuario(Model model) {
        try {// Manejo de errorres
            org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext()
                    .getAuthentication();
            String usuario = authentication.getName();// usuario que saca la cita
            Iterable<citasModel> citas = repository.findByUsuario(usuario);
            System.out.println(citas.toString());
            model.addAttribute("citas", citas);
            return "citas/micita";
        } catch (Exception e) {
            System.out.println(e.toString());
            return "theme/error";
        }
    }

    @PostMapping("/guardarCita")
    public String guardarCita(@ModelAttribute citasModel cita, RedirectAttributes attributes) {
        try {// Guarda la cita en base de datos
            org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext()
                    .getAuthentication();
            String usuario = authentication.getName();// usuario que saca la cita
            System.out.println(this.getClass() + "------USuario ____" + authentication.getName());
            cita.setUsuario(usuario);
            if (cita.getEstado().equals("Cancelada")) {
                cita.setFechaCancelacion(LocalDate.now().toString());
            }
            repository.save(cita);
            boolean isAdmin = authentication.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ADMIN")); 
            String page = "";
            if (isAdmin) {
                page = "redirect:/citas/";
            } else {
                page = "redirect:/citas/micita";
            }
            attributes.addFlashAttribute("success", "Registro guardado con exito!");
            return page;
        } catch (Exception e) {
            System.out.println(e.toString());
            return "theme/error";
        }
    }

    @GetMapping("/editar/{id}")
    public String editarCita(@PathVariable("id") Long id, Model model) {
        try {// Carga los datos de la cita por el ID enviado por parametro
            citasModel cita = repository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("ID De CITA no valido"));
            Iterable<medicoModel> medicos = medicoRepository.findAll();
            model.addAttribute("medicos", medicos);
            model.addAttribute("cita", cita);
            return "citas/editar";
        } catch (Exception e) {
            System.out.println(e.toString());
            return "theme/error";
        }
    }

}
