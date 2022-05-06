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
	public String addCart(@RequestParam Integer id, @RequestParam Integer cantidad, Model model) {
		//parametros : id y la cantidad
		
		DetalleOrden detalleOrden = new DetalleOrden();
		Producto producto=new Producto();
		double sumaTotal=0; //suma de productos agregados
		
		Optional<Producto> optionalProducto = productoService.get(id);
		LOGGER.info("el producto añadido: {}" , optionalProducto.get());
		LOGGER.info("cantidad {}" , cantidad);
		producto = optionalProducto.get();
		
		detalleOrden.setCantidad(cantidad);
		detalleOrden.setPrecio(producto.getPrecio());
		detalleOrden.setNombre(producto.getNombre());
		detalleOrden.setTotal(producto.getPrecio() * cantidad);
		detalleOrden.setProducto(producto); //id y llave foreana
		
		//validacion para que el producto no se agregue mas de una vez
		Integer idProducto = producto.getId();
		//si encuentra alguna coincidencia
		boolean ingresado = detalles.stream().anyMatch(p -> p.getProducto().getId() == idProducto);
		
		if(!ingresado) {//si es true añade sino no
			detalles.add(detalleOrden);
		}
		
		
		
		sumaTotal = detalles.stream().mapToDouble(dt->dt.getTotal()).sum();
		
		orden.setTotal(sumaTotal);
		model.addAttribute("cart", detalles);
		model.addAttribute("orden", orden);
		
		return "usuario/carrito";
	}
	
	//quitar un producto del carrito
	@GetMapping("/delete/cart/{id}")
	public String deleteProductoCart(@PathVariable Integer id, Model model) {
		//lista nueva de productos
		List<DetalleOrden> ordenesNueva = new ArrayList<DetalleOrden>();
		for(DetalleOrden detalleOrden: detalles) {
			if(detalleOrden.getProducto().getId()!=id) {
				//si encuentra un id que ya este en  detalles
				//no se añade a la nueva orden
				ordenesNueva.add(detalleOrden);
			}
		}
		//poner la nueva lista con los productos restantes
		detalles=ordenesNueva;
		
		double sumaTotal = 0;
		
		sumaTotal = detalles.stream().mapToDouble(dt->dt.getTotal()).sum();
		orden.setTotal(sumaTotal);
		model.addAttribute("cart", detalles);
		model.addAttribute("orden", orden);
		
		return "usuario/carrito";
	}
	
	@GetMapping("/getCart")
	public String getCart(Model model) {
		
		
		model.addAttribute("cart", detalles);
		model.addAttribute("orden", orden);
		
		
		return "usuario/carrito";
	}
	

}
