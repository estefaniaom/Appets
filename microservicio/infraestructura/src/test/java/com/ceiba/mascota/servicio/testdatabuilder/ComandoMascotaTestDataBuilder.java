package com.ceiba.mascota.servicio.testdatabuilder;

import java.time.LocalDateTime;

import com.ceiba.mascota.comando.ComandoMascota;

public class ComandoMascotaTestDataBuilder {

	private String nombre;
	private LocalDateTime fecha;
	private String contacto;
	private String telefono;
	private String tipo;
	
	public ComandoMascotaTestDataBuilder(){
		nombre = "Pantera";
		fecha = LocalDateTime.now();
		contacto = "Jhon Doe";
		telefono = "3152000000";
		tipo = "Gato";
	}
	
	public ComandoMascota build(){
		return new ComandoMascota(nombre,fecha,contacto,telefono,tipo);
	}
}
