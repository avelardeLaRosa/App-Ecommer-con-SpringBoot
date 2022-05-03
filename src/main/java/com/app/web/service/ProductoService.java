package com.app.web.service;

import java.util.List;
import java.util.Optional;

import com.app.web.model.Producto;

public interface ProductoService {
	
	
	public Optional<Producto> get(int id); //optional valida si el objeto existe o no en la bd 
	public Producto save(Producto p);
	public void update(Producto p);
	public void delete(int id);
	public List<Producto>findAll();

}
