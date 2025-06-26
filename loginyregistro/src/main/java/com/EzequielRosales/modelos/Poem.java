package com.EzequielRosales.modelos; // Paquete corregido

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;

@Entity
@Table(name = "poemas")
public class Poem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El título no puede estar vacío.")
    @Size(min = 5, message = "El título debe tener al menos 5 caracteres.")
    @Pattern(regexp = "^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ!?., ]*$", message = "El título no debe contener caracteres especiales excesivos.")
    private String title;

    @NotBlank(message = "El autor no puede estar vacío.")
    @Size(min = 3, message = "El autor debe tener al menos 3 caracteres.")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]*$", message = "El autor no debe contener caracteres especiales.")
    private String author;

    @NotBlank(message = "El contenido no puede estar vacío.")
    @Size(min = 15, message = "El contenido del poema debe tener al menos 15 caracteres.")
    private String content;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // Relación Many-to-One con Usuario: Un poema es creado por un Usuario.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false) // Clave foránea en la tabla 'poemas'
    private User usuario; // ¡Debe ser 'usuario' para el mappedBy en Usuario.java!

    public Poem() {
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
