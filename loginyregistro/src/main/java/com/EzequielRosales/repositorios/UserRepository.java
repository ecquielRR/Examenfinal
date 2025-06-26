package com.EzequielRosales.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

import com.EzequielRosales.modelos.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
   
    Optional<User> findByCorreo(String correo);
    
   
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.correo = :correo")
    boolean existsByCorreo(@Param("correo") String correo);
    
    @Query("SELECT u FROM User u WHERE u.createdAt >= :fecha")
    List<User> findUsersRegisteredAfter(@Param("fecha") LocalDateTime fecha);
    
    @Query("SELECT COUNT(u) FROM User u")
    long countTotalUsers();
    
    
    @Query("SELECT u FROM User u WHERE LOWER(u.nombre) LIKE LOWER(CONCAT('%', :termino, '%')) OR LOWER(u.apellido) LIKE LOWER(CONCAT('%', :termino, '%'))")
    List<User> findByNombreOrApellidoContainingIgnoreCase(@Param("termino") String termino);
}