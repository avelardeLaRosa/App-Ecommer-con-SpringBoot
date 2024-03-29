package com.app.web.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.web.model.Producto;
import com.app.web.repository.ProductoRepository;

@Service
public class ProductoServiceImpl implements ProductoService {
	
	//inyecttio de repositorio producto para el crud
	@Autowired
	private ProductoRepository productoRepository;

	@Override
	@Transactional(readOnly = true)
	public Optional<Producto> get(int id) {
		
		return productoRepository.findById(id);
	}

	@Override
	@Transactional
	public Producto save(Producto p) {
		// TODO Auto-generated method stub
		return productoRepository.save(p);
	}

	@Override
	@Transactional
	public void update(Producto p) {
		// TODO Auto-generated method stub
		productoRepository.save(p);
	}

	@Override
	@Transactional
	public void delete(int id) {
		// TODO Auto-generated method stub
		productoRepository.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Producto> findAll() {
		// TODO Auto-generated method stub
		return (List<Producto>) productoRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Producto> findByNombre(String term) {
		// TODO Auto-generated method stub
		return productoRepository.findByNombreLikeIgnoreCase("%"+term+"%");
	}


}
