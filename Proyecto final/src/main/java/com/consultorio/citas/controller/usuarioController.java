package com.consultorio.citas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.consultorio.citas.model.usuarioModel;
import com.consultorio.citas.repository.usuarioRepository;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class usuarioController {
    /*
     * Se instancia el repositorio
     */
    @Autowired
    private usuarioRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping("/")
    public String index() {

        return "usuario/login";
    }

    /*
     * Carga el formulario de registro
     * 
     */
    @GetMapping("/registrarse")
    public String registrarse(Model model) {
        model.addAttribute("usuario", new usuarioModel());
        return "usuario/registro";
    }

    /*
     * Guarda el usuario, en caso de existir muetra un error general
     */
    @PostMapping("/guardarUsuario")
    public String guardarUsuario(@ModelAttribute usuarioModel usuario, RedirectAttributes attributes) {
        try {// Manejo de errorres
       
            if (repository.findById(usuario.getCedula()).isEmpty()) {/* Valida si exite un registro admin */
                usuario.setContrasenna(passwordEncoder.encode(usuario.getContrasenna()));
                repository.save(usuario);
                attributes.addFlashAttribute("success", "Registro guardado con exito!");
               
            } else {
                attributes.addFlashAttribute("error", "Registro ya existe!");
            }

            return  "redirect:/usuarios";
        } catch (Exception e) {
            System.out.println(e.toString());
            return "theme/error";
        }
    }

    @PostMapping("/guardarRegistro")
    public String guardarRegistro(@ModelAttribute usuarioModel usuario) {
        try {// Manejo de errorres
            String page = "";
            if (repository.findById(usuario.getCedula()).isEmpty()) {/* Valida si exite un registro admin */
                usuario.setContrasenna(passwordEncoder.encode(usuario.getContrasenna()));
                repository.save(usuario);
                page = "redirect:/";// Si el usuario no existe lo guarda e inicia sesión
            } else {
                page = "theme/error"; // Si el usuario existe, envia un error general
            }

            return page;
        } catch (Exception e) {
            System.out.println(e.toString());
            return "theme/error";
        }
    }

    /*
     * Muetra los usuarios creados
     */
    // @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping("/usuarios")
    public String listarUsuarios(Model model) {

        Iterable<usuarioModel> usuarios = repository.findAll();
        model.addAttribute("usuarios", usuarios);
        return "usuario/index";
    }

    /*
     * Carga los datos del usuario
     * 
     */
    @GetMapping("/usuariosEditar/{cedula}")
    public String editarUsuario(@PathVariable("cedula") String cedula, Model model) {
        try {// Manejo de errorres
            usuarioModel usuario = repository.findById(cedula)
                    .orElseThrow(() -> new IllegalArgumentException("ID De usuario no valido"));
            model.addAttribute("usuario", usuario);
            return "usuario/editar";
        } catch (Exception e) {
            System.out.println(e.toString());
            return "theme/error";
        }
    }

        /*
     * Carga el fomulario con la información del usuario para poder editar la información
     * 
     */
    @GetMapping("/miInformacion")
    public String miInformacion( Model model) {
        try {// Manejo de errorres
            org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext()
                    .getAuthentication();
            String cedula=authentication.getName();
            boolean isAdmin = authentication.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ADMIN")); //obtienen el rol de la sesión 
            model.addAttribute("isAdmin",isAdmin);
            usuarioModel usuario = repository.findById(cedula)
                    .orElseThrow(() -> new IllegalArgumentException("ID De usuario no valido"));
            model.addAttribute("usuario", usuario);
            return "usuario/editar";
        } catch (Exception e) {
            System.out.println(e.toString());
            return "theme/error";
        }
    }

    /*
     * Actualiza la información del usuario
     */
    @PostMapping("/editarUsuario")
    public String editarUsuario(@ModelAttribute usuarioModel usuario, RedirectAttributes attributes) {
        try {// Manejo de errorres
            System.out.println(usuario.getApellidos());
            usuario.setContrasenna(passwordEncoder.encode(usuario.getContrasenna()));
            repository.save(usuario);
            attributes.addFlashAttribute("success", "Registro guardado con exito!");

            return "redirect:/usuarios";
        } catch (Exception e) {
            System.out.println(e.toString());
            return "theme/error";
        }
    }
/*
 * 
 * Formulario para el registro interno de usuarios
 */
    @GetMapping("/nuevoUsuario")
    public String nuevoUsuario(Model model) {
        model.addAttribute("usuario", new usuarioModel());
        return "usuario/nuevo";
    }

    /*
     * 
     * Pagina home
     */
    @RequestMapping("/admin")
    public String admin(Model model) {
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        System.out.println(this.getClass() + "-------Datos ____" + authentication.getDetails());
        System.out.println(this.getClass() + "------Autenticado ____" + authentication.isAuthenticated());
        System.out.println(this.getClass() + "------Nombre ____" + authentication.getName());
        return "home/admin";
    }
/*
 * Pagina de error 
 * 
 */
    @RequestMapping("/errors")
    public String error(Model model) {
        model.addAttribute("loginError", true);
        return "home/error";
    }
}
