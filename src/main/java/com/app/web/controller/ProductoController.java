package com.app.web.controller;

import java.io.IOException;
import java.util.Optional;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.app.web.model.Producto;
import com.app.web.model.Usuario;
import com.app.web.service.ProductoService;
import com.app.web.service.UpdloadFileService;

@Controller
@RequestMapping("/productos")
public class ProductoController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(ProductoController.class);

	@Autowired
	private ProductoService productoService;
	@Autowired
	private UpdloadFileService uploadService;
	
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
	public String save(Producto producto,@RequestParam("img") MultipartFile file) throws IOException {
		LOGGER.info("Este es el objeto producto {}", producto);
		Usuario u=new Usuario(1,"","","","","","",""); //setea para la fk
		producto.setUsuarios(u); //le da la data al fk
		
		
		//GUARDAR IMAGEN
		if(producto.getId()==null) {
			//cuando se crea un producto
			String nombreImagen=uploadService.saveImage(file);
			producto.setImagen(nombreImagen);
		}else {
			
		}
		productoService.save(producto);
		return "redirect:/productos";
	}
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable Integer id,Model model) {
		//pathVariable mapea la variable q viene en la url
		Producto producto = new Producto();
		Optional<Producto> optionalProducto = productoService.get(id);
		//optional nos devuele algo de la busqueda
		producto= optionalProducto.get();
		
		LOGGER.info("producto buscado: {}", producto);
		
		model.addAttribute("producto", producto);
		return "productos/edit";
	}
	
	@PostMapping("/update")
	public String update(Producto producto,@RequestParam("img") MultipartFile file) throws IOException {
		if(file.isEmpty()) {//editamos el producto pero no cambiamos la imagen
			Producto p = new Producto();
			p = productoService.get(producto.getId()).get();
			producto.setImagen(p.getImagen());
		}else {//cuando se edita tbm la imagen
			Producto p = new Producto();
			p = productoService.get(producto.getId()).get();
			if(!p.getImagen().equals("default.jpg")) {
				//eliminar si la img no sea el por defecto
				uploadService.deleteImage(p.getImagen());
			}
			
			
			String nombreImagen = uploadService.saveImage(file);
			producto.setImagen(nombreImagen);
		}
		productoService.update(producto);
		return "redirect:/productos";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Integer id) {
		Producto p = new Producto();
		p = productoService.get(id).get();
		if(!p.getImagen().equals("default.jpg")) {
			//eliminar si la img no sea el por defecto
			uploadService.deleteImage(p.getImagen());
		}
		productoService.delete(id);
		return "redirect:/productos";
	}
	
	

	
	
	
	
}
