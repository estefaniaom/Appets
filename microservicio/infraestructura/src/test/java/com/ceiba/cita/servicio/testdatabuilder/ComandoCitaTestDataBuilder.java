package com.ceiba.cita.servicio.testdatabuilder;

import java.time.LocalDateTime;

import com.ceiba.cita.comando.ComandoCita;

public class ComandoCitaTestDataBuilder {

	private Long id;
	private LocalDateTime fecha;
	private String servicio;
	private Long idMascota;
	private String nombre;
	
	public ComandoCitaTestDataBuilder(){
		fecha = LocalDateTime.now();
		servicio = "PELUQUERIA";
		idMascota = 1L;
		nombre = "Sky";
	}
	public ComandoCita build(){
		return new ComandoCita(id, fecha, servicio, idMascota, nombre);
	}
	
}
