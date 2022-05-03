package com.app.web.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.web.model.Producto;
import com.app.web.repository.ProductoRepository;

@Service
public class ProductoServiceImpl implements ProductoService {
	
	//inyecttio de repositorio producto para el crud
	@Autowired
	private ProductoRepository productoRepository;

	@Override
	public Optional<Producto> get(int id) {
		
		return productoRepository.findById(id);
	}

	@Override
	public Producto save(Producto p) {
		// TODO Auto-generated method stub
		return productoRepository.save(p);
	}

	@Override
	public void update(Producto p) {
		// TODO Auto-generated method stub
		productoRepository.save(p);
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		productoRepository.deleteById(id);
	}

}
