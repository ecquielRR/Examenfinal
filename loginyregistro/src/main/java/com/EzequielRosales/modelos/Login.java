package com.EzequielRosales.modelos; 

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class Login {

    @NotBlank(message = "El correo no puede estar vacío.")
    @Email(message = "Debe ser un formato de correo electrónico válido.")
    @Size(max = 100, message = "El correo no puede exceder 100 caracteres.")
    private String correo;

    @NotBlank(message = "La contraseña no puede estar vacía.")
    @Size(min = 8, max = 100, message = "La contraseña debe tener entre 8 y 100 caracteres.")
    private String contrasena;

    
    private boolean recordarme = false;

    public Login() {
    }

    public Login(String correo, String contrasena) {
        this.correo = correo;
        this.contrasena = contrasena;
    }

    
    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo != null ? correo.trim().toLowerCase() : null;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public boolean isRecordarme() {
        return recordarme;
    }

    public void setRecordarme(boolean recordarme) {
        this.recordarme = recordarme;
    }

    
    public boolean isValid() {
        return correo != null && !correo.trim().isEmpty() && 
               contrasena != null && !contrasena.trim().isEmpty();
    }
}