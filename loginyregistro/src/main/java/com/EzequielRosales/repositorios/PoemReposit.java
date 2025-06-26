package com.EzequielRosales.repositorios;

import com.EzequielRosales.modelos.Poem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PoemReposit extends JpaRepository<Poem, Long> {
	 List<Poem> findByUsuarioId(Long usuarioId);

     List<Poem> findByAuthorOrderByTitleAsc(String author);
}