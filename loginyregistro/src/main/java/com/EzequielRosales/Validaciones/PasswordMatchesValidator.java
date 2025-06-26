package com.EzequielRosales.Validaciones;

import com.EzequielRosales.modelos.User; // Asegúrate de importar tu clase Usuario
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Implementación de la lógica de validación para la anotación @PasswordMatches.
 * Verifica que la contraseña y la confirmación de contraseña en el objeto Usuario coincidan.
 */
public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
        
    }

    /**
     * Lógica principal de validación.
     *
     * @param obj El objeto que está siendo validado (se espera que sea una instancia de Usuario).
     * @param context Contexto para construir mensajes de error.
     * @return true si las contraseñas coinciden, false en caso contrario.
     */
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        // Se asume que el objeto que se está validando es de tipo Usuario
        User usuario = (User) obj;

        
        boolean isValid = usuario.getContrasena() != null &&
                          usuario.getContrasena().equals(usuario.getConfirmarContraseña());

        
        if (!isValid) {
            context.disableDefaultConstraintViolation(); // Deshabilita el mensaje de error por defecto
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                    .addPropertyNode("confirmarContraseña") // Asocia el error al campo 'confirmarContraseña'
                    .addConstraintViolation(); // Añade la violación de la restricción
        }
        return isValid;
    }
}
