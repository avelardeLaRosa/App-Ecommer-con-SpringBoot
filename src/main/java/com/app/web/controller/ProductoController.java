package com.app.web.controller;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app.web.model.Producto;
import com.app.web.model.Usuario;
import com.app.web.service.ProductoService;

@Controller
@RequestMapping("/productos")
public class ProductoController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(ProductoController.class);

	@Autowired
	private ProductoService productoService;
	
	@GetMapping("")
	public String show(Model model) {
		//el model lleva info del backend hacia la vista
		model.addAttribute("productos", productoService.findAll());
		//productos recibe la data de service,findall y lo eniv a  la vista
		return "productos/show";
	}
	
	@GetMapping("/create")
	public String create() {
		return "productos/create";
	}
	
	@PostMapping("/save")
	public String save(Producto producto) {
		LOGGER.info("Este es el objeto producto {}", producto);
		Usuario u=new Usuario(1,"","","","","","",""); //setea para la fk
		producto.setUsuarios(u); //le da la data al fk
		productoService.save(producto);
		return "redirect:/productos";
	}
	
}
