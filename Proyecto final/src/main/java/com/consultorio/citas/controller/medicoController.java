package com.consultorio.citas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.consultorio.citas.model.medicoModel;
import com.consultorio.citas.repository.medicoRepository;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class medicoController {
    /*
     * Se instancia el repositorio
     */
    @Autowired
    private medicoRepository repository;

    /*
     * Guarda el medico, en caso de existir muetra un error general
     */
    @PostMapping("/guardarMedico")
    public String guardarmedico(@ModelAttribute medicoModel medico, RedirectAttributes attributes) {
        try {// Guarda el medico en base de datos
            if (repository.findById(medico.getCedula()).isEmpty()) {/* Valida si exite un registro*/
                repository.save(medico);
                attributes.addFlashAttribute("success", "Registro guardado con exito!");//Despliega mensaje si el registro se guardó correctamente
            } else {
                attributes.addFlashAttribute("error", "Registro ya existe!");//Si el registro existe muestra mensaje de error
            }

            return "redirect:/medicos";
        } catch (Exception e) {
            System.out.println(e.toString());
            return "theme/error";
        }
    }

    /*
     * Muetra todos los medicos creados
     */
    @RequestMapping("/medicos")
    public String listarMedico(Model model) {

        Iterable<medicoModel> medicos = repository.findAll();
        model.addAttribute("medicos", medicos);
        return "medico/index";
    }

    /*
     * Carga los datos del medico por identificación
     * 
     */
    @GetMapping("/medicoEditar/{cedula}")
    public String editarMedicoFRM(@PathVariable("cedula") String cedula, Model model) {
        try {// Manejo de errorres
            medicoModel medico = repository.findById(cedula)
                    .orElseThrow(() -> new IllegalArgumentException("Medico no existe"));
            model.addAttribute("medico", medico);
            return "medico/editar";
        } catch (Exception e) {
            System.out.println(e.toString());
            return "theme/error";
        }
    }
    /*
     * Actualiza la información del medico
     */
    @PostMapping("/editarMedico")
    public String editarMedico(@ModelAttribute medicoModel medico, RedirectAttributes attributes) {
        try {// Manejo de errorres
            repository.save(medico);
            attributes.addFlashAttribute("success", "Registro guardado con exito!");

            return "redirect:/medicos";
        } catch (Exception e) {
            System.out.println(e.toString());
            return "theme/error";
        }
    }
/*
 * 
 * Formulario que carga los datos del medico
 */
    @GetMapping("/nuevoMedico")
    public String nuevoUsuario(Model model) {
        model.addAttribute("medico", new medicoModel());
        return "medico/nuevo";
    }

}
