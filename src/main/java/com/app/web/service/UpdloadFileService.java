package com.app.web.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UpdloadFileService {
	
	//tendra la ubicacion donde se cargaran las imagenes
	private String folder="images//";
	
	public String saveImage(MultipartFile file) throws IOException {
		if(!file.isEmpty()) {
			//transformacion de img en bytes
			byte [] bytes=file.getBytes();
			//ruta donde se almacenara la imagen ingresada
			Path path= Paths.get(folder+file.getOriginalFilename());
			//escribir en directorio (directorio y los bytes de la img)
			Files.write(path, bytes);
			return file.getOriginalFilename();
		}
		
		return "default.jpg";
	}
	
	
	public void deleteImage(String nombreImg) {
		String ruta = "images//";
		//ruta
		File file=new File(ruta+nombreImg);
		file.delete();
	}

}
