package com.app.web.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.app.web.model.Producto;

public interface ProductoService {
	
	
	public Optional<Producto> get(int id); //optional valida si el objeto existe o no en la bd 
	public Producto save(Producto p);
	public void update(Producto p);
	public void delete(int id);
	public List<Producto>findAll();
	
	public List<Producto> findByNombre(String term);

	

}
