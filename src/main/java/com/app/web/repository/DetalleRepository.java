package com.app.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.app.web.model.DetalleOrden;

@Repository
public interface DetalleRepository extends JpaRepository<DetalleOrden, Integer> {
	
	

}
