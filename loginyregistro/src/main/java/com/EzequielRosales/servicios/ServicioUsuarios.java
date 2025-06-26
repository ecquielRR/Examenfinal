package com.EzequielRosales.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EzequielRosales.modelos.User;
import com.EzequielRosales.repositorios.UserReposit;

@Service
public class ServicioUsuarios {
    @Autowired
    private UserReposit repositorioUsuarios;

    public ServicioUsuarios(UserReposit repositorioUsuarios) {
        this.repositorioUsuarios = repositorioUsuarios;
    }

    public User agregarUsuario(User nuevoUsuario) {
        return this.repositorioUsuarios.save(nuevoUsuario);
    }

    public User obtenerUsuarioPorCorreo(String correo) {
        return this.repositorioUsuarios.findByCorreo(correo).orElse(null);
    }

    public User obtenerUsuarioPorId(Long id) {
        return this.repositorioUsuarios.findById(id).orElse(null);
    }
}

