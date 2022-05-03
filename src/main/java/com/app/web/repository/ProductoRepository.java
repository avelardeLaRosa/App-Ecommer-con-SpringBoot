package com.app.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.web.model.Producto;

@Repository //su finalidad es ser inyectado en service
public interface ProductoRepository extends JpaRepository<Producto, Integer> {
	
	
	
	

}
