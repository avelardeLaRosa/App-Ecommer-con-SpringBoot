package com.app.web.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app.web.model.DetalleOrden;
import com.app.web.model.Orden;
import com.app.web.model.Producto;
import com.app.web.model.Usuario;
import com.app.web.service.DetalleService;
import com.app.web.service.OrdenService;
import com.app.web.service.ProductoService;
import com.app.web.service.UsuarioService;

@Controller
@RequestMapping("/administrador")
public class AdministradorController {

	@Autowired
	private ProductoService productoService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private OrdenService ordenService;
	
	@Autowired
	private DetalleService detalleService;
	
	@GetMapping("/index")
	public String prueba() {
		return "administrador/template_admin2";
	}
	
	@GetMapping("")
	public String home(Model model,HttpSession session) {
		
		//List<Producto>productos= productoService.findAll();
		//model.addAttribute("productos",productos);
		model.addAttribute("session",session.getAttribute("idusuario"));
		
		return "administrador/home";//directorio administrador / pagia home
	}
	
	
	@GetMapping("/usuarios")
	public String usuarios(Model model) {
		List<Usuario> usuarios = usuarioService.findAll();
		model.addAttribute("usuarios",usuarios);
		return "administrador/usuarios";
	}
	
	@GetMapping("/ordenes")
	public String ordenes(Model model) {
		model.addAttribute("ordenes", ordenService.findAll());
		return "administrador/ordenes";
	}
	
	@GetMapping("/detalle/{id}")
	public String detalle(Model model, @PathVariable("id")int id) {
		Orden orden = ordenService.findById(id).get();
		model.addAttribute("detalles",orden.getDetalle());
		model.addAttribute("id",String.valueOf(orden.getNumero()));
		return "administrador/detalleorden";
	}
	
	@GetMapping("/eliminar/{id}")
	public String eliminardetalle(Model model, @PathVariable("id")int id) {
		ordenService.delete(id);
		return "redirect:/administrador/ordenes";
	}
	
	
	@GetMapping("/editar/{id}")
	public String editardetalle(Model model, @PathVariable("id")int id) {
		Orden orden= ordenService.findById(id).get();
		model.addAttribute("orden",orden.getDetalle());
		return "administrador/editar_orden";
	}
	
	@PostMapping("/actualizar")
	public String actualizardetalle(@PathVariable("id")int id ,DetalleOrden orden,Model model) {
		Orden ordenAct= ordenService.findById(id).get();
		ordenAct.setDetalle(orden);
		detalleService.save(ordenAct.getDetalle());
		return "administrador/editar_orden";
	}
}
