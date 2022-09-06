package com.app.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.concurrent.MonoToListenableFutureAdapter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.app.web.model.DetalleOrden;
import com.app.web.model.Orden;
import com.app.web.model.Producto;
import com.app.web.model.Usuario;
import com.app.web.paginador.PageRender;
import com.app.web.service.DetalleService;
import com.app.web.service.OrdenService;
import com.app.web.service.ProductoService;
import com.app.web.service.UsuarioService;
import com.app.web.util.Sha2;
import com.app.web.util.VPOS2;

@Controller
@RequestMapping("/") //este controlador mapeare la raiz del proyecto
public class HomeController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);


	@Autowired
	private ProductoService productoService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private OrdenService ordenService;
	
	@Autowired
	private DetalleService detalleService;
	
	@Autowired
	private Sha2 claveSha;
	
	
	
	//para almacenar los detalles de la orden
	List<DetalleOrden> detalles = new ArrayList<DetalleOrden>();
	//el obj almacenara los datos de la orden
	Orden orden = new Orden();
	
	@GetMapping("") //este metodo llama a la raiz
	public String home( Model model, HttpSession session) {
		
		
		
		List<Producto> productos = productoService.findAll();
		model.addAttribute("productos",productos);
		
		model.addAttribute("session", session.getAttribute("idusuario"));
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
		detalleOrden.setProducto(producto); //llave foranea de producto en la tabla

		//validacion para que el producto no se agregue mas de una vez
		Integer idProducto = producto.getId();
		String proImagen = producto.getImagen();
		//si encuentra alguna coincidencia
		boolean ingresado = detalles.stream().anyMatch(p -> p.getProducto().getId() == idProducto);
		
		
		if(!ingresado) {//si es true añade sino no
			//detalleOrden.setCantidad(producto.getCantidad());
			
			
			detalles.add(detalleOrden);
			
			
			
		}
		
		//suma el s/.total de productos en la lista
		sumaTotal = detalles.stream().mapToDouble(dt->dt.getTotal()).sum();
		
		orden.setTotal(sumaTotal);
		String monto = String.valueOf(String.format("%.2f",orden.getTotal()));
		model.addAttribute("simbolo","S/. ");
		
		model.addAttribute("cart", detalles);
		model.addAttribute("orden", orden);
		
		return "usuario/carrito";
	}
	

	
	@PostMapping("/actualizar/cart")
	public String actualizarProductoCart(DetalleOrden detalle) {
		detalleService.save(detalle);
		return "redirect:/usuario/carrito";
		
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
		model.addAttribute("simbolo","S/. ");
		return "usuario/carrito";
	}
	
	@GetMapping("/getCart")
	public String getCart(Model model, HttpSession session) {
		
		
		model.addAttribute("cart", detalles);
		model.addAttribute("orden", orden);
		model.addAttribute("simbolo","S/. ");
		
		//session
		model.addAttribute("session", session.getAttribute("idusuario"));
		
		return "usuario/carrito";
	}
	
	@GetMapping("order")
	public String order(Model model, HttpSession session) {
		
		int id = Integer.parseInt(session.getAttribute("idusuario").toString());
		
		Usuario usuario = usuarioService.findById(id).get();
		
		model.addAttribute("usuario",usuario);
		model.addAttribute("cart", detalles);
		model.addAttribute("orden", orden);
		
		return "usuario/resumenorden";
	}
	
	@GetMapping("/saveOrder")
	public String saveOrder(HttpSession session,Model model) {
		String nomProducto = null;
		
		Date fechaCreacion = new Date();
		orden.setFechaCreacion(fechaCreacion);
		orden.setNumero(ordenService.getNumeroOrden());	
		int id = Integer.parseInt(session.getAttribute("idusuario").toString());
		//usuario
		Usuario usuario = usuarioService.findById(id).get();
		orden.setUsuario(usuario);
		ordenService.save(orden);
		//guardar detalles
		for(DetalleOrden dt: detalles) {
			
			dt.setOrden(orden);
			detalleService.save(dt);
		}
		
		
		
		
		/***************************ABRIR PASARELA***************/
		/*Numero de operacion random*/
		int random = (int)(Math.random()*10000+1);
		String acquirerId = "144"; //144 Integraciones / 29 Produccion
		String idCommerce = "13272";
		
		String monto = String.valueOf(String.format("%.2f",orden.getTotal()));
		String total = monto.replace(",", "");
		String purchaseAmount = total ;
		
		
				//String.valueOf(orden.getTotal())
		String purchaseCurrencyCode = "604";  //604 SOLES Y DOLARES 840
		String purchaseOperationNumber = String.valueOf(random);


		String purchaseVerificationComercio = claveSha.getSHA512(acquirerId
				+ idCommerce
				+ purchaseOperationNumber
				+ purchaseAmount
				+ purchaseCurrencyCode
				+ "fLnXBCwnNAJmLRestDZ_494473784798");
		
		
		
		model.addAttribute("acquirerId", acquirerId);
		model.addAttribute("idCommerce", idCommerce);
		model.addAttribute("purchaseAmount", purchaseAmount);
		model.addAttribute("purchaseCurrencyCode", purchaseCurrencyCode);
		model.addAttribute("random", purchaseOperationNumber);
		model.addAttribute("codigo", purchaseVerificationComercio);
		
		model.addAttribute("nombre",usuario.getNombre());
		model.addAttribute("apellido",usuario.getApellido());
		model.addAttribute("email",usuario.getEmail());
		model.addAttribute("direccion",usuario.getDireccion());
		
	
		//limpiar lista
				orden = new Orden();
				detalles.clear();

		return "pasarela/procesar";
	}
	
	@PostMapping("/search")
	public String buscarProducto(@RequestParam String nombre, Model model) {
		
		//si la lista de productos contiene el nombre que se envia los convierte en lista y los devuelve
		List<Producto> productos = productoService.findAll()
				.stream()
				.filter(p -> p.getNombre().contains(nombre))
				.collect(Collectors.toList());
		
		model.addAttribute("productos", productos);
		
		return "usuario/home";
		
	}
	
	

}
