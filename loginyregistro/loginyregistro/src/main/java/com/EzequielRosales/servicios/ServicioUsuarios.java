package com.EzequielRosales.servicios;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.EzequielRosales.modelos.User;
import com.EzequielRosales.repositorios.UserRepository;

@Service
public class ServicioUsuarios {
    
    private static final Logger logger = LoggerFactory.getLogger(ServicioUsuarios.class);
    
    private final UserRepository repositorioUsuarios;
    private final BCryptPasswordEncoder passwordEncoder;

  
    public ServicioUsuarios(UserRepository repositorioUsuarios, BCryptPasswordEncoder passwordEncoder) {
        this.repositorioUsuarios = repositorioUsuarios;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User agregarUsuario(User nuevoUsuario) {
        logger.info("Intentando registrar nuevo usuario con correo: {}", nuevoUsuario.getCorreo());
        
        
        validarUsuarioUnico(nuevoUsuario.getCorreo());
        
        // Encriptar la contraseña
        String contraseñaEncriptada = passwordEncoder.encode(nuevoUsuario.getContrasena());
        nuevoUsuario.setContrasena(contraseñaEncriptada);
        
        // Limpiar el campo de confirmación antes de guardar
        nuevoUsuario.setConfirmarContraseña(null);
        
        User usuarioGuardado = this.repositorioUsuarios.save(nuevoUsuario);
        logger.info("Usuario registrado exitosamente con ID: {}", usuarioGuardado.getId());
        
        return usuarioGuardado;
    }

    public User obtenerUsuarioPorCorreo(String correo) {
        logger.debug("Buscando usuario por correo: {}", correo);
        if (correo == null || correo.trim().isEmpty()) {
            throw new IllegalArgumentException("El correo no puede estar vacío");
        }
        return this.repositorioUsuarios.findByCorreo(correo.toLowerCase().trim()).orElse(null);
    }

    public User obtenerUsuarioPorId(Long id) {
        logger.debug("Buscando usuario por ID: {}", id);
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo");
        }
        return this.repositorioUsuarios.findById(id).orElse(null);
    }

    
    public boolean existeCorreo(String correo) {
        return repositorioUsuarios.existsByCorreo(correo.toLowerCase().trim());
    }

    public void validarUsuarioUnico(String correo) {
        if (existeCorreo(correo)) {
            logger.warn("Intento de registro con correo ya existente: {}", correo);
            throw new IllegalArgumentException("Ya existe un usuario registrado con este correo electrónico");
        }
    }

    public boolean verificarCredenciales(String correo, String contraseña) {
        logger.debug("Verificando credenciales para: {}", correo);
        User usuario = obtenerUsuarioPorCorreo(correo);
        
        if (usuario == null) {
            logger.warn("Intento de login con correo no registrado: {}", correo);
            return false;
        }
        
        boolean credencialesValidas = passwordEncoder.matches(contraseña, usuario.getContrasena());
        if (credencialesValidas) {
            logger.info("Login exitoso para usuario: {}", correo);
        } else {
            logger.warn("Contraseña incorrecta para usuario: {}", correo);
        }
        
        return credencialesValidas;
    }

    public long contarTotalUsuarios() {
        return repositorioUsuarios.countTotalUsers();
    }

    
    @Transactional
    public boolean cambiarContraseña(Long userId, String contraseñaActual, String nuevaContraseña) {
        User usuario = obtenerUsuarioPorId(userId);
        if (usuario == null) {
            return false;
        }
        
        if (!passwordEncoder.matches(contraseñaActual, usuario.getContrasena())) {
            return false;
        }
        
        usuario.setContrasena(passwordEncoder.encode(nuevaContraseña));
        repositorioUsuarios.save(usuario);
        logger.info("Contraseña cambiada para usuario ID: {}", userId);
        
        return true;
    }
}