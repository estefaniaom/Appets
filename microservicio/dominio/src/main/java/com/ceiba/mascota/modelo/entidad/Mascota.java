package com.ceiba.mascota.modelo.entidad;

import java.time.LocalDateTime;

import lombok.Getter;
@Getter
public class Mascota {

	private Long id;
	private String nombre;
	private LocalDateTime fechaNacimiento;
	private String nombreContacto;
	private String telefono;
	private String tipo;
	
	public Mascota(Long id, String nombre, LocalDateTime fechaNacimiento, String nombreContacto, String telefono, String tipo) {
		this.id = id;
		this.nombre = nombre;
		this.fechaNacimiento = fechaNacimiento;
		this.nombreContacto = nombreContacto;
		this.telefono = telefono;
		this.tipo = tipo;
	}
}
