package com.app.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app.web.service.ProductoService;

@Controller
@RequestMapping("/") //este controlador mapeare la raiz del proyecto
public class HomeController {
	
	@Autowired
	private ProductoService productoService;
	
	@GetMapping("") //este metodo llama a la raiz
	public String homre(Model model) {
		model.addAttribute("productos",productoService.findAll());
		return "usuario/home";
	}

}
