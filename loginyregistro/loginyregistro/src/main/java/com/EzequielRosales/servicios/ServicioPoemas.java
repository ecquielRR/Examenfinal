package com.EzequielRosales.servicios;

import java.util.List;

import org.springframework.stereotype.Service;

import com.EzequielRosales.modelos.Poem;
import com.EzequielRosales.repositorios.PoemRepository;



@Service
public class ServicioPoemas {

	private final PoemRepository repositorioPoemas;

	public ServicioPoemas(PoemRepository repositorioPoemas) {
		this.repositorioPoemas = repositorioPoemas;
	}

	
	public List<Poem> obtenerTodosLosPoemas() {
		return repositorioPoemas.findAll();
	}

	
	public Poem obtenerPorId(Long id) { 
		return repositorioPoemas.findById(id).orElse(null); 
	}

		public Poem agregarPoema(Poem poema) { 
		return repositorioPoemas.save(poema);
	}

	
	
	public Poem editarPoema(Poem poema) { 
		if (!repositorioPoemas.existsById(poema.getId())) {
			throw new IllegalArgumentException("Poema con ID " + poema.getId() + " no encontrado para actualizar.");
		}
		return repositorioPoemas.save(poema); 
		
	}

	
	
	public void eliminarPoema(Long id) { 
		if (!repositorioPoemas.existsById(id)) {
			throw new IllegalArgumentException("Poema con ID " + id + " no encontrado para eliminar.");
		}
		repositorioPoemas.deleteById(id);
	}


	public List<Poem> obtenerTodosLosPoemasPorUsuario(Long idUsuario) { 
		
		return repositorioPoemas.findByUsuarioId(idUsuario);
	}

	
	public List<Poem> buscarPoemasPorAutorOrdenadoPorTitulo(String author) { // Renombrado para consistencia
		return repositorioPoemas.findByAuthorOrderByTitleAsc(author);
	}
}