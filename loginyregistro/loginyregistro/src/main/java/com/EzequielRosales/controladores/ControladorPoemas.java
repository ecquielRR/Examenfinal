package com.EzequielRosales.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.EzequielRosales.modelos.Poem;
import com.EzequielRosales.modelos.User;
import com.EzequielRosales.servicios.ServicioPoemas;
import com.EzequielRosales.servicios.ServicioUsuarios;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class ControladorPoemas {

    private final ServicioPoemas servicioPoemas;
    private final ServicioUsuarios servicioUsuarios;

    @Autowired
    public ControladorPoemas(ServicioPoemas servicioPoemas, ServicioUsuarios servicioUsuarios) {
        this.servicioPoemas = servicioPoemas;
        this.servicioUsuarios = servicioUsuarios;
    }

    @GetMapping("/poemas")
    public String poemas(Model modelo, HttpSession sesion) {
        if (sesion.getAttribute("nombre") == null) {
            return "redirect:/login";
        }
        List<Poem> listaDePoemas = this.servicioPoemas.obtenerTodosLosPoemas();
        modelo.addAttribute("listaDePoemas", listaDePoemas);
        return "poemas.jsp";
    }

    @GetMapping("/misPoemas/{idUsuario}")
    public String misPoemas(@PathVariable("idUsuario") Long idUsuario, Model modelo, HttpSession sesion) {
        if (sesion.getAttribute("nombre") == null) {
            return "redirect:/login";
        }
        List<Poem> listaDePoemas = this.servicioPoemas.obtenerTodosLosPoemasPorUsuario(idUsuario);
        modelo.addAttribute("listaDePoemas", listaDePoemas);
        return "misPoemas.jsp";
    }

    @GetMapping("/detalle/poema/{idPoema}")
    public String detallePoema(@PathVariable("idPoema") Long idPoema, Model modelo, HttpSession sesion) {
        if (sesion.getAttribute("id_usuario") == null) {
            return "redirect:/login";
        }
        Poem poema = this.servicioPoemas.obtenerPorId(idPoema);
        modelo.addAttribute("poema", poema);
        return "detallePoema.jsp";
    }

    @GetMapping("/agregar/poema")
    public String agregarPoema(@ModelAttribute("poema") Poem poema, HttpSession sesion) {
        if (sesion.getAttribute("nombre") == null) {
            return "redirect:/login";
        }
        return "agregarPoema.jsp";
    }

    @GetMapping("editar/poema/{idPoema}")
    public String editarPoema(@ModelAttribute("poema") Poem editarPoema, Model modelo, HttpSession sesion,
            @PathVariable("idPoema") Long idPoema) {
        if (sesion.getAttribute("nombre") == null) {
            return "redirect:/login";
        }
        Poem poema = servicioPoemas.obtenerPorId(idPoema);
        modelo.addAttribute("poema", poema);
        return "editarPoema.jsp";
    }

    @PostMapping("/procesar/agregar/poema")
    public String procesarAgregarPoema(@Valid @ModelAttribute("poema") Poem poema, BindingResult validacion,
            HttpSession sesion) {
        if (validacion.hasErrors()) {
            return "agregarPoema.jsp";
        }
        Long idUsuario = (Long) sesion.getAttribute("id_usuario");
        User usuarioExistente = this.servicioUsuarios.obtenerUsuarioPorId(idUsuario);
        poema.setUsuario(usuarioExistente);
        this.servicioPoemas.agregarPoema(poema);
        return "redirect:/poemas";
    }

    @DeleteMapping("/eliminar/poema/{idPoema}")
    public String eliminarPoema(@PathVariable("idPoema") Long idPoema) {
        this.servicioPoemas.eliminarPoema(idPoema);
        return "redirect:/poemas";
    }

    @PutMapping("/editar/poema/{idPoema}")
    public String editarPoema(@Valid @ModelAttribute("poema") Poem poema, BindingResult validacion,
            @PathVariable("idPoema") Long idPoema, HttpSession sesion) {
        if (validacion.hasErrors()) {
            return "editarPoema.jsp";
        }
        Long idUsuario = (Long) sesion.getAttribute("id_usuario");
        User usuario = this.servicioUsuarios.obtenerUsuarioPorId(idUsuario);
        Poem poemaActual = this.servicioPoemas.obtenerPorId(idPoema);
        poema.setId(poemaActual.getId());
        poema.setUsuario(usuario);
        this.servicioPoemas.editarPoema(poema);
        return "redirect:/poemas";
    }
}
