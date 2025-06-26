package com.EzequielRosales.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import com.EzequielRosales.modelos.User;

@Repository
public interface UserReposit extends CrudRepository<User, Long>{
	
	
	Optional<User> findByCorreo(String correo);
}