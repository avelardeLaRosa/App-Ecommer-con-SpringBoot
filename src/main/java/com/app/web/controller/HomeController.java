package com.app.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.app.web.model.DetalleOrden;
import com.app.web.model.Orden;
import com.app.web.model.Producto;
import com.app.web.service.ProductoService;

@Controller
@RequestMapping("/") //este controlador mapeare la raiz del proyecto
public class HomeController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private ProductoService productoService;
	
	//para almacenar los detalles de la orden
	List<DetalleOrden> detalles = new ArrayList<DetalleOrden>();
	//el obj almacenara los datos de la orden
	Orden orden = new Orden();
	
	@GetMapping("") //este metodo llama a la raiz
	public String home(Model model) {
		model.addAttribute("productos",productoService.findAll());
		return "usuario/home";
	}
	
	@GetMapping("productohome/{id}")
	public String productoHome(@PathVariable Integer id, Model model) {
		LOGGER.info("Id producto enviado {}",id);
		Producto producto = new Producto();
		Optional<Producto> productoOptional = productoService.get(id);
		producto = productoOptional.get();
		
		model.addAttribute("producto",producto);
		return "usuario/productohome";
	}
	
	@PostMapping("/cart")
	public String addCart(@RequestParam Integer id, @RequestParam Integer cantidad) {
		//parametros : id y la cantidad
		
		DetalleOrden detalleOrden = new DetalleOrden();
		Producto producto=new Producto();
		double sumaTotal=0; //suma de productos agregados
		
		Optional<Producto> optionalProducto = productoService.get(id);
		LOGGER.info("el producto añadido: {}" , optionalProducto.get());
		LOGGER.info("cantidad {}" , cantidad);
		
		return "usuario/carrito";
	}
	
	

}
