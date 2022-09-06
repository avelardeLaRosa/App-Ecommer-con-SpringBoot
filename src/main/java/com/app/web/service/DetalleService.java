package com.app.web.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.web.model.DetalleOrden;
import com.app.web.model.Usuario;

public interface DetalleService{

	public DetalleOrden save(DetalleOrden detalleOrden);
	
	public Optional<DetalleOrden> findById(Integer id);
}
