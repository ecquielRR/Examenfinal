package com.EzequielRosales.controladores;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.EzequielRosales.modelos.Login; // Importar el DTO de Login
import com.EzequielRosales.modelos.User; // Importar el modelo Usuario
import com.EzequielRosales.servicios.ServicioUsuarios; // Importar el servicio correcto (plural)

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class ControladorUsuarios {
	
	// Constante para el nombre del atributo de modelo de registro
	private static final String ATRIBUTO_REGISTRO = "usuarioRegistro"; 
	// Constante para el nombre del atributo de modelo de login
	private static final String ATRIBUTO_LOGIN = "usuarioLogin";

	@Autowired
	private final ServicioUsuarios servicioUsuarios; // Nombre del servicio corregido

	public ControladorUsuarios(ServicioUsuarios servicioUsuarios) { // Nombre del servicio corregido
		this.servicioUsuarios = servicioUsuarios;
	}
	
	/**
	 * Muestra el formulario de registro de usuario.
	 *
	 * @param usuario Modelo para el formulario de registro.
	 * @return Nombre de la vista JSP para el formulario de registro.
	 */
	@GetMapping("/registro")
	public String despliegaFormularioRegistro(@ModelAttribute(ATRIBUTO_REGISTRO) User usuario) { // CORREGIDO: Usar Usuario
		return "registro.jsp"; 
	}
	
	/**
	 * Procesa el envío del formulario de registro.
	 * Realiza validaciones y registra al usuario.
	 *
	 * @param nuevoUsuario Datos del nuevo usuario para registrar.
	 * @param validaciones Objeto para manejar errores de validación.
	 * @param sesion Sesión HTTP para almacenar datos del usuario logueado.
	 * @return Redirección a la página principal o de vuelta al formulario de registro si hay errores.
	 */
	@PostMapping("/procesa/registro")
	public String procesaRegistro(@Valid @ModelAttribute(ATRIBUTO_REGISTRO) User nuevoUsuario, // CORREGIDO: Usar Usuario
								  BindingResult validaciones,
								  HttpSession sesion,
								  RedirectAttributes redirectAttributes) {

		// Verifica si el correo ya está en uso
		User usuarioExistente = this.servicioUsuarios.obtenerUsuarioPorCorreo(nuevoUsuario.getCorreo()); // CORREGIDO: Usar getCorreo()
		if(usuarioExistente != null) {
			validaciones.rejectValue("correo", "errorCorreoExistente", "Este correo ya está en uso.");
		}
		
		// La validación personalizada @PasswordMatches en el modelo Usuario ya maneja esto,
		// pero se mantiene aquí para una verificación adicional o si la anotación no se usa.
		if(! nuevoUsuario.getContrasena().equals(nuevoUsuario.getConfirmarContraseña())) { // CORREGIDO: getContrasena(), getConfirmarContraseña()
			validaciones.rejectValue("confirmarContraseña", "errorContraseñasNoCoinciden", "Las contraseñas deben ser iguales.");
		}
		
		if(validaciones.hasErrors()) {
			return "registro.jsp"; 
		}
		
		// Encriptar la contraseña
		String contrasenaEncriptada = BCrypt.hashpw(nuevoUsuario.getContrasena(), BCrypt.gensalt());
		nuevoUsuario.setContrasena(contrasenaEncriptada);
		
		// Agregar usuario
		User usuarioRegistrado = this.servicioUsuarios.agregarUsuario(nuevoUsuario); // CORREGIDO: Usuario
		
		// Establecer atributos de sesión
		sesion.setAttribute("id_usuario", usuarioRegistrado.getId());
		sesion.setAttribute("nombre", usuarioRegistrado.getNombre() + " " + usuarioRegistrado.getApellido());
		
		redirectAttributes.addFlashAttribute("successMessage", "Registro exitoso. Por favor, inicia sesión.");
		return "redirect:/login"; 
	}
	
	/**
	 * Muestra el formulario de inicio de sesión.
	 *
	 * @param usuarioLogin Modelo para el formulario de login.
	 * @return Nombre de la vista JSP para el formulario de login.
	 */
	@GetMapping("/login")
	public String despliegaLoginUsuario(@ModelAttribute(ATRIBUTO_LOGIN) Login usuarioLogin, Model model, HttpSession session) { // CORREGIDO: Usar Login
		// Si el usuario ya está logueado, redirigir a la página de poemas
        if (session.getAttribute("id_usuario") != null) {
            return "redirect:/poemas";
        }
        // Limpiar mensajes de error/éxito si existen en la sesión (ej. de RedirectAttributes)
        model.addAttribute("successMessage", session.getAttribute("successMessage"));
        model.addAttribute("errorMessage", session.getAttribute("errorMessage"));
        session.removeAttribute("successMessage");
        session.removeAttribute("errorMessage");
		return "login.jsp"; 
	}
	
	/**
	 * Procesa el envío del formulario de inicio de sesión.
	 * Autentica al usuario y establece la sesión.
	 *
	 * @param usuarioLogin Credenciales de login.
	 * @param validaciones Objeto para manejar errores de validación.
	 * @param sesion Sesión HTTP para almacenar datos del usuario logueado.
	 * @return Redirección a la página principal o de vuelta al formulario de login si hay errores.
	 */
	@PostMapping("/procesa/login")
	public String procesaLoginUsuario(@Valid @ModelAttribute(ATRIBUTO_LOGIN) Login usuarioLogin, // CORREGIDO: Usar Login
									  BindingResult validaciones,
									  HttpSession sesion,
									  RedirectAttributes redirectAttributes) {

		if(validaciones.hasErrors()) {
			return "login.jsp";
		}
		
		User usuarioExistente = this.servicioUsuarios.obtenerUsuarioPorCorreo(usuarioLogin.getCorreo()); // CORREGIDO: Usar getCorreo()
		if(usuarioExistente == null) {
			validaciones.rejectValue("correo", "errorCorreoNoExiste", "No existe un usuario con el correo proporcionado.");
			return "login.jsp"; 
		}
		
		// Verificar la contraseña encriptada
		if(! BCrypt.checkpw(usuarioLogin.getContrasena(), usuarioExistente.getContrasena())) { // CORREGIDO: getContrasena()
			validaciones.rejectValue("contrasena", "errorContraseñaIncorrecta", "Contraseña incorrecta.");
			return "login.jsp"; 
		}
		
		// Si no hay errores, establecer atributos de sesión
		sesion.setAttribute("id_usuario", usuarioExistente.getId());
		sesion.setAttribute("nombre", usuarioExistente.getNombre() + " " + usuarioExistente.getApellido()); 
		
		redirectAttributes.addFlashAttribute("successMessage", "¡Bienvenido, " + usuarioExistente.getNombre() + "!");
		return "redirect:/poemas"; 
	}
	
	/**
	 * Cierra la sesión del usuario.
	 *
	 * @param sesion Sesión HTTP a invalidar.
	 * @return Redirección a la página de login.
	 */
	@GetMapping("/procesa/logout") 
	public String procesaLogout(HttpSession sesion, RedirectAttributes redirectAttributes) {
		sesion.invalidate();
		redirectAttributes.addFlashAttribute("successMessage", "Has cerrado sesión exitosamente."); 
		return "redirect:/login";
	}
}