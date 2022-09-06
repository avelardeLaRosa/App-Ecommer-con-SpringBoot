package com.app.web.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.web.model.Orden;
import com.app.web.model.Usuario;
import com.app.web.repository.OrdenRepository;

@Service
public class OrdenServiceImpl implements OrdenService{

	@Autowired
	private OrdenRepository ordenRepository;
	
	@Override
	@Transactional
	public Orden save(Orden orden) {
		// TODO Auto-generated method stub
		return ordenRepository.save(orden);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Orden> findAll() {
		// TODO Auto-generated method stub
		return (List<Orden>) ordenRepository.findAll();
	}
	
	public String getNumeroOrden() {
		
		int numero = 0;
		
		String numeroConcatenado = "";
		
		List<Orden> ordenes = findAll();
		
		List<Integer> numeros = new ArrayList<>();
		
		ordenes.stream().forEach(o -> numeros.add(Integer.parseInt(o.getNumero())));
		
		if(ordenes.isEmpty()) {
			numero= 1;
		}else {
			//mayor numero de la lista de ordenes 
			numero = numeros.stream().max(Integer::compare).get();
			numero ++; //al ultimo numero se le aumenta 1
		}
		
		if(numero<10) {
			numeroConcatenado = "00000"+String.valueOf(numero);
		}else if(numero < 100) {
			numeroConcatenado = "0000"+String.valueOf(numero);
		}else if(numero < 1000) {
			numeroConcatenado = "000"+String.valueOf(numero);
		}else if(numero < 10000) {
			numeroConcatenado = "00"+String.valueOf(numero);
		}
		
		return numeroConcatenado;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Orden> findByUsuario(Usuario usuario) {
		// TODO Auto-generated method stub
		return ordenRepository.findByUsuario(usuario);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Orden> findById(Integer id) {
		// TODO Auto-generated method stub
		return ordenRepository.findById(id);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		ordenRepository.deleteById(id);
	}
	

}
