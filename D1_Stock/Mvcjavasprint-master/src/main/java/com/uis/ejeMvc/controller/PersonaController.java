package com.uis.ejeMvc.controller;

import com.uis.ejeMvc.model.Persona;
import com.uis.ejeMvc.model.Tipodocumento;
import com.uis.ejeMvc.repository.PersonaRepositoryInter;
import com.uis.ejeMvc.repository.TipodocumentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class PersonaController {
    @Autowired
    private PersonaRepositoryInter personaRepositoryInter;

    @Autowired
    private TipodocumentoRepository tipodocumentoRepository;

    @GetMapping("/personas")
    public String ListaPersonas(Model model){
        List<Persona> listaPersona = personaRepositoryInter.findAll();
        model.addAttribute("listaPersona", listaPersona);
        return "personas";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioDeNuevaPersona(Model model){
        List<Tipodocumento> listaTipodocumento = tipodocumentoRepository.findAll();

        model.addAttribute("persona", new Persona());
        model.addAttribute("listaTipodocumento", listaTipodocumento);
        return "persona_formulario";
    }

    @PostMapping("/guardar")
    public String guardarPersona(Persona persona){
        personaRepositoryInter.save(persona);
        return "redirect:/personas";
    }

    @GetMapping("/inicio")
    public String regreso() {
        return "index";
    }

    @GetMapping("/personas/eliminar/{id}")
    public String deletePersona(@PathVariable ("id") Long id, Model model){
        personaRepositoryInter.deleteById(id);
        return "redirect:/personas";
    }

    @GetMapping("/personas/editar/{id}")
    public String EditarPersona(@PathVariable("id") Long id, Model modelo){
        Persona persona = personaRepositoryInter.findById(id).get();
        modelo.addAttribute("persona", persona);

        List<Tipodocumento> listaTipodocumento = tipodocumentoRepository.findAll();
        modelo.addAttribute("listaTipodocumento", listaTipodocumento);

        return "persona_formulario";
    }

    @PostMapping("/login_gerente")
    public String loginGerente(@RequestParam String usuario, @RequestParam String contrasena, Model model) {
        Persona persona = personaRepositoryInter.findByUsuarioAndContrasena(usuario, contrasena);

        if (persona != null) {
            return "gerente";
        } else {
            model.addAttribute("error", "Usuario o contraseña incorrectos");
            return "redirect:/sesion_gerente";
        }
    }

    @GetMapping("/sesion_gerente")
    public String sesionGerente() {
        return "inicio_sesion_gerente";
    }

    @PostMapping("/login_empleado")
    public String loginEmpleado(@RequestParam String usuario, @RequestParam String contrasena, Model model) {
        Persona persona = personaRepositoryInter.findByUsuarioAndContrasena(usuario, contrasena);

        if (persona != null) {
            return "empleado";
        } else {
            model.addAttribute("error", "Usuario o contraseña incorrectos");
            return "redirect:/sesion_empleado";
        }
    }

    @GetMapping("/sesion_empleado")
    public String sesionEmpleado() {
        return "inicio_sesion_empleado";
    }

}
