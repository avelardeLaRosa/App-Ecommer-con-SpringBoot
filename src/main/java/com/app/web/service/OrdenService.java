package com.app.web.service;

import java.util.List;
import java.util.Optional;

import com.app.web.model.Orden;
import com.app.web.model.Usuario;

public interface OrdenService {
	
	public Orden save (Orden orden);
	
	public List<Orden> findAll();
	
	public String getNumeroOrden();
	
	public List<Orden> findByUsuario(Usuario usuario);
	
	public Optional<Orden> findById(Integer id);
	
	public void delete(Integer id);
}
