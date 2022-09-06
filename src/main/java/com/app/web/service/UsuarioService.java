package com.app.web.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;


import com.app.web.model.Usuario;
import com.app.web.repository.UsuarioRepository;


public interface UsuarioService {
	
	public List<Usuario> findAll();
	
	public Optional<Usuario> findById(Integer id);
	
	public Usuario guardarUsuario(Usuario usuario);
	
	public Optional<Usuario> findByEmail(String email);
	
	public void delete(Integer id);

}
