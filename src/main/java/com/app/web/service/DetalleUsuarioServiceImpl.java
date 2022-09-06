package com.app.web.service;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.web.model.Usuario;

@Service
public class DetalleUsuarioServiceImpl implements UserDetailsService {

	@Autowired
	private UsuarioService usuarioService;
	
	@Lazy
	@Autowired
	private BCryptPasswordEncoder encriptacion;
	
	@Autowired
	HttpSession session;
	
	private final Logger LOGGER =  LoggerFactory.getLogger(DetalleUsuarioServiceImpl.class);
	
	@Override //carga el usuario a traves del username
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		Optional<Usuario> usuario = usuarioService.findByEmail(username);
		if(usuario.isPresent()) {
			session.setAttribute("idusuario", usuario.get().getId());
			
			
			Usuario u = usuario.get();
			
			return User.builder().username(u.getEmail()).password(u.getPassword()).roles(u.getTipo()).build();
			
		}else {
			throw new UsernameNotFoundException("Usuario no existe en la BD!");
		}
		
	}

}
