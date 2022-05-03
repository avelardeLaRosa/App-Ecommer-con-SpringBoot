package com.app.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app.web.model.Producto;
import com.app.web.service.ProductoService;

@Controller
@RequestMapping("/administrador")
public class AdministradorController {

	@Autowired
	private ProductoService productoService;
	
	@GetMapping("")
	public String home(Model model) {
		
		//List<Producto>productos= productoService.findAll();
		//model.addAttribute("productos",productos);
		
		model.addAttribute("productos", productoService.findAll());
		return "administrador/home";//directorio administrador / pagia home
	}
	
	
}
