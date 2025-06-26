package com.EzequielRosales.controladores;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.EzequielRosales.modelos.Login;
import com.EzequielRosales.modelos.User;
import com.EzequielRosales.servicios.ServicioUsuarios;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class ControladorLoginYRegistro {
	
	@Autowired
	private final ServicioUsuarios servicioUsuarios;

	public ControladorLoginYRegistro(ServicioUsuarios servicioUsuarios) {
		this.servicioUsuarios = servicioUsuarios;
	}
	
	@GetMapping("/registro")
	public String registro(@ModelAttribute("registro") User usuario, HttpSession sesion) {
		
		if (sesion.getAttribute("id_usuario") != null) {
			return "redirect:/poemas";
		}
		return "registro.jsp";
	}
	
	@GetMapping("/login")
	public String login(@ModelAttribute("login") Login usuarioLogin, HttpSession sesion) {
		
		if (sesion.getAttribute("id_usuario") != null) {
			return "redirect:/poemas";
		}
		return "login.jsp";
	}
	
	@PostMapping("/procesar/registro")
	public String procesarRegistro(@Valid @ModelAttribute("registro") User nuevoUsuario, 
									BindingResult validacion, 
									HttpSession sesion,
									RedirectAttributes redirectAttributes) {
		
		
		User usuarioExistente = this.servicioUsuarios.obtenerUsuarioPorCorreo(nuevoUsuario.getCorreo());
		if(usuarioExistente != null) {
			validacion.rejectValue("correo", "correoExistente", "Este correo ya está en uso.");
		}
		
		
		
		if (validacion.hasErrors()) {
			return "registro.jsp";
		}
		
		try {
			
			String contrasenaEncriptada = BCrypt.hashpw(nuevoUsuario.getContrasena(), BCrypt.gensalt());
			nuevoUsuario.setContrasena(contrasenaEncriptada);
			
			
			User usuarioRegistrado = this.servicioUsuarios.agregarUsuario(nuevoUsuario);
			
			
			sesion.setAttribute("id_usuario", usuarioRegistrado.getId());
			sesion.setAttribute("nombre", usuarioRegistrado.getNombre());
			sesion.setAttribute("apellido", usuarioRegistrado.getApellido());
			
			redirectAttributes.addFlashAttribute("successMessage", 
				"¡Bienvenido " + usuarioRegistrado.getNombre() + "! Tu cuenta ha sido creada exitosamente.");
			
			return "redirect:/poemas";
			
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("errorMessage", 
				"Error al crear la cuenta. Por favor, intenta nuevamente.");
			return "redirect:/registro";
		}
	}
	
	@PostMapping("/procesar/login")
	public String procesarLogin(@Valid @ModelAttribute("login") Login loginUsuario,
								BindingResult validacion,
								HttpSession sesion,
								RedirectAttributes redirectAttributes) {

		if (validacion.hasErrors()) {
			return "login.jsp";
		}

		try {
			User usuarioExistente = this.servicioUsuarios.obtenerUsuarioPorCorreo(loginUsuario.getCorreo());
			
			if (usuarioExistente == null) {
				validacion.rejectValue("correo", "correoNoExistente", "No existe una cuenta con este correo.");
				return "login.jsp";
			}

			if (!BCrypt.checkpw(loginUsuario.getContrasena(), usuarioExistente.getContrasena())) {
				validacion.rejectValue("contrasena", "credencialIncorrecta", "Contraseña incorrecta.");
				return "login.jsp";
			}
			
			
			sesion.setAttribute("id_usuario", usuarioExistente.getId());
			sesion.setAttribute("nombre", usuarioExistente.getNombre());
			sesion.setAttribute("apellido", usuarioExistente.getApellido());

			redirectAttributes.addFlashAttribute("successMessage", 
				"¡Bienvenido de vuelta, " + usuarioExistente.getNombre() + "!");

			return "redirect:/poemas";
			
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("errorMessage", 
				"Error al iniciar sesión. Por favor, intenta nuevamente.");
			return "redirect:/login";
		}
	}
	
	@PostMapping("/cerrar/sesion")
	public String cerrarSesion(HttpSession sesion, RedirectAttributes redirectAttributes) {
		try {
			sesion.invalidate();
			redirectAttributes.addFlashAttribute("successMessage", "Has cerrado sesión exitosamente.");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("errorMessage", "Error al cerrar sesión.");
		}
		return "redirect:/login";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession sesion, RedirectAttributes redirectAttributes) {
		return cerrarSesion(sesion, redirectAttributes);
	}
}