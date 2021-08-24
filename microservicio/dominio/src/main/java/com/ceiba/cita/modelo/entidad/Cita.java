package com.ceiba.cita.modelo.entidad;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class Cita {

	private Long id;
	private LocalDateTime fecha;
	private String servicio;
	private Long idMascota;
	private String nombre;
	private Long precio;
	
	public Cita(Long id, LocalDateTime fecha, String servicio, Long idMascota, String nombre) {
		this.id = id;
		this.fecha = fecha;
		this.servicio = servicio;
		this.idMascota = idMascota;
		this.nombre = nombre;
	}
}
