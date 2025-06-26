package com.EzequielRosales.modelos;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;

@Entity
@Table(name = "poemas", indexes = {
    @Index(name = "idx_poema_usuario", columnList = "usuario_id"),
    @Index(name = "idx_poema_author", columnList = "author"),
    @Index(name = "idx_poema_created", columnList = "created_at")
})
public class Poem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El título no puede estar vacío.")
    @Size(min = 5, max = 100, message = "El título debe tener entre 5 y 100 caracteres.")
    @Pattern(regexp = "^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ!?., ]*$", 
             message = "El título no debe contener caracteres especiales excesivos.")
    @Column(nullable = false, length = 100)
    private String title;

    @NotBlank(message = "El autor no puede estar vacío.")
    @Size(min = 3, max = 50, message = "El autor debe tener entre 3 y 50 caracteres.")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]*$", 
             message = "El autor no debe contener caracteres especiales.")
    @Column(nullable = false, length = 50)
    private String author;

    @NotBlank(message = "El contenido no puede estar vacío.")
    @Size(min = 15, max = 5000, message = "El contenido del poema debe tener entre 15 y 5000 caracteres.")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Relación Many-to-One con Usuario
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private User usuario;

    
    @Version
    private Long version;

    public Poem() {
    }

    
    public Poem(String title, String author, String content, User usuario) {
        this.title = title;
        this.author = author;
        this.content = content;
        this.usuario = usuario;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

   
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    
    public String getPreview() {
        return content.length() > 100 ? content.substring(0, 100) + "..." : content;
    }
}