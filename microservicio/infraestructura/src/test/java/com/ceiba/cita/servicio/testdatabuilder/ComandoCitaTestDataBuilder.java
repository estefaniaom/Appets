package com.ceiba.cita.servicio.testdatabuilder;

import java.time.LocalDateTime;

import com.ceiba.cita.comando.ComandoCita;

public class ComandoCitaTestDataBuilder {

	private LocalDateTime fecha;
	private String servicio;
	private Long idMascota;
	
	public ComandoCitaTestDataBuilder(){
		fecha = LocalDateTime.now();
		servicio = "PELUQUERIA";
		idMascota = 1L;
	}
	public ComandoCita build(){
		return new ComandoCita(fecha, servicio, idMascota);
	}
	
}
