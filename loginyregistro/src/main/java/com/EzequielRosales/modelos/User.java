package com.EzequielRosales.modelos; 

import com.EzequielRosales.Validaciones.PasswordMatches; 
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "usuarios", uniqueConstraints = @UniqueConstraint(columnNames = "correo"))
@PasswordMatches 
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío.")
    @Size(min = 2, message = "El nombre debe tener al menos 2 caracteres.")
    private String nombre;

    @NotBlank(message = "El apellido no puede estar vacío.")
    @Size(min = 2, message = "El apellido debe tener al menos 2 caracteres.")
    private String apellido;

    @NotBlank(message = "El correo no puede estar vacío.")
    @Email(message = "Debe ser un formato de correo electrónico válido.")
    private String correo;

    @NotBlank(message = "La contraseña no puede estar vacía.")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres.")
    
    private String contrasena;

    @Transient 
    @NotBlank(message = "La confirmación de contraseña no puede estar vacía.")
    private String confirmarContraseña;

 
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Poem> poemas; 

    public User() {
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getConfirmarContraseña() {
        return confirmarContraseña;
    }

    public void setConfirmarContraseña(String confirmarContraseña) {
        this.confirmarContraseña = confirmarContraseña;
    }

    public List<Poem> getPoemas() {
        return poemas;
    }

    public void setPoemas(List<Poem> poemas) {
        this.poemas = poemas;
    }
}
