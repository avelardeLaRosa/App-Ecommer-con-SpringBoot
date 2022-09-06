package com.app.web.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.web.model.DetalleOrden;
import com.app.web.repository.DetalleRepository;

@Service
public class DetalleServiceImpl implements DetalleService {
	
	@Autowired
	private DetalleRepository detalleRepository;

	@Override
	@Transactional
	public DetalleOrden save(DetalleOrden detalleOrden) {
		// TODO Auto-generated method stub
		return detalleRepository.save(detalleOrden);
	}

	@Override
	public Optional<DetalleOrden> findById(Integer id) {
		// TODO Auto-generated method stub
		return detalleRepository.findById(id);
	}

	
	
	
	
}
