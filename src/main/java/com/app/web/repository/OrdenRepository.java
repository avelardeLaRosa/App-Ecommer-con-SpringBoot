package com.app.web.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.app.web.model.Orden;
import com.app.web.model.Usuario;

@Repository
public interface OrdenRepository extends JpaRepository<Orden, Integer>{
	
	
	List<Orden> findByUsuario(Usuario usuario);

}
