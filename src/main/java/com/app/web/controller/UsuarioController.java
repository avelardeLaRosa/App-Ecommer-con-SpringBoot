package com.app.web.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app.web.model.DetalleOrden;
import com.app.web.model.Orden;
import com.app.web.model.Usuario;
import com.app.web.service.OrdenService;
import com.app.web.service.UsuarioService;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private OrdenService ordenService;
	
	BCryptPasswordEncoder passEncode=new BCryptPasswordEncoder();
	
	private final Logger LOGGER=LoggerFactory.getLogger(UsuarioController.class);
	
	@GetMapping("/registro")
	public String create() {
		
		return "usuario/registro";
	}
	
	@GetMapping("/registroAdmin")
	public String crear() {
		
		return "usuario/registro2";
	}
	
	@PostMapping("/save")
	public String save(Usuario usuario) {
		
		usuario.setTipo("USER");
		usuario.setPassword(passEncode.encode(usuario.getPassword()));
		usuarioService.guardarUsuario(usuario);
		return "redirect:/usuario/login";
	}
	
	@PostMapping("/guardar")
	public String guardar(Usuario usuario) {
		usuario.setTipo("ADMIN");
		usuario.setPassword(passEncode.encode(usuario.getPassword()));
		usuarioService.guardarUsuario(usuario);
		return "redirect:/administrador/usuarios";
	
	}
	
	@GetMapping("/editar/{id}")
	public String editar(@PathVariable Integer id, Model model) {
		Usuario usuario = new Usuario();
		Optional<Usuario> optionalUsuario = usuarioService.findById(id);
		usuario = optionalUsuario.get();
		
		model.addAttribute("usuario", usuario);

		
		return "administrador/editar_usuario";
	}
	
	@PostMapping("/actualizar")
	public String actualizar(Usuario usuario) {
		
		
		usuario.setPassword(passEncode.encode(usuario.getPassword()));
		usuarioService.guardarUsuario(usuario);
		
		return "redirect:/administrador/usuarios";
		
	}
	
	
	
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable("id")Integer id) {
		
		usuarioService.delete(id);
		
		return "redirect:/administrador/usuarios";
				
	}
	
	@GetMapping("/login")
	public String login() {
		
		return "usuario/login";
	}
	
	@GetMapping("/acceder")
	public String acceder(Usuario usuario, HttpSession session) {
		
		Optional<Usuario> user = usuarioService.findById(Integer.parseInt(session.getAttribute("idusuario").toString()));
		LOGGER.info("Id usuario:",user.get().getId());
		if(user.isPresent()) {
			//si esque hay registro con ese email
			session.setAttribute("idusuario", user.get().getId());//id usuario 
			if(user.get().getTipo().equalsIgnoreCase("ADMIN")) {
				
				return "redirect:/administrador";
			}else{
				return "redirect:/";
			}
		}
		
		return "usuario/login";
	}
	
	
	
	@GetMapping("/compras")
	public String obtenerCompras(Model model, HttpSession session) {
		
		Usuario usuario = usuarioService.findById(Integer.parseInt(session.getAttribute("idusuario").toString())).get();
		List<Orden>ordenes = ordenService.findByUsuario(usuario);
		model.addAttribute("session", session.getAttribute("idusuario"));
		model.addAttribute("ordenes", ordenes);
		return "usuario/compras";
	}
	
	
	@GetMapping("/detalle/{id}")
	public String detalleCompra(@PathVariable Integer id, HttpSession session, Model model) {
		
		Optional<Orden>orden = ordenService.findById(id);
		model.addAttribute("session", session.getAttribute("idusuario"));
		model.addAttribute("detalles", orden.get().getDetalle());
		return "usuario/detallecompra";
	}
	
	@GetMapping("/cerrar")
	public String cerrarSesion(HttpSession session) {
		session.removeAttribute("idusuario");
		return "redirect:/usuario/login";
	}
	
	
	
	
	
}
