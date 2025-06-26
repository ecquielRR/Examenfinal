package com.EzequielRosales.repositorios;

import com.EzequielRosales.modelos.Poem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PoemRepository extends JpaRepository<Poem, Long> {
    
    
    List<Poem> findByUsuarioId(Long usuarioId);
    List<Poem> findByAuthorOrderByTitleAsc(String author);
    
    
    Page<Poem> findByUsuarioId(Long usuarioId, Pageable pageable);
    Page<Poem> findByAuthorOrderByTitleAsc(String author, Pageable pageable);
    
    
    @Query("SELECT p FROM Poem p WHERE p.usuario.id = :usuarioId ORDER BY p.createdAt DESC")
    List<Poem> findByUsuarioIdOrderByCreatedAtDesc(@Param("usuarioId") Long usuarioId);
    
    @Query("SELECT p FROM Poem p WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Poem> findByTitleContainingIgnoreCase(@Param("keyword") String keyword);
    
    @Query("SELECT p FROM Poem p WHERE LOWER(p.content) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Poem> findByContentContainingIgnoreCase(@Param("keyword") String keyword);
    
    @Query("SELECT p FROM Poem p WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(p.content) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Poem> findByTitleOrContentContainingIgnoreCase(@Param("keyword") String keyword);
    
   
    @Query("SELECT COUNT(p) FROM Poem p WHERE p.usuario.id = :usuarioId")
    long countByUsuarioId(@Param("usuarioId") Long usuarioId);
    
    @Query("SELECT p FROM Poem p WHERE p.createdAt >= :fecha")
    List<Poem> findPoemsCreatedAfter(@Param("fecha") LocalDateTime fecha);
    
    
    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM Poem p WHERE p.id = :poemId AND p.usuario.id = :usuarioId")
    boolean existsByIdAndUsuarioId(@Param("poemId") Long poemId, @Param("usuarioId") Long usuarioId);
   
    @Query("SELECT p FROM Poem p WHERE p.author = :author ORDER BY p.createdAt DESC")
    List<Poem> findTop10ByAuthorOrderByCreatedAtDesc(@Param("author") String author, Pageable pageable);
}