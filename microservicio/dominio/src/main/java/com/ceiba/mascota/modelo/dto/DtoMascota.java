package com.ceiba.mascota.modelo.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DtoMascota {

	private Long id;
	private String nombre;
	private LocalDateTime fechaNacimiento;
	private String nombreContacto;
	private String telefono;
	private String tipo;
	
	private LocalDateTime fechaProximaVacunaRabia;
	private LocalDateTime fechaProximaVacunaTripleFelina;
	private LocalDateTime fechaProximaDesparacitacion;
	private LocalDateTime fechaProximaPeluqueria;
	
	public DtoMascota(Long id, String nombre, LocalDateTime fechaNacimiento, String nombreContacto, String telefono, String tipo){
		this.id=id;
		this.nombre = nombre;
		this.fechaNacimiento = fechaNacimiento;
		this.nombreContacto = nombreContacto;
		this.telefono = telefono;
		this.tipo = tipo;
	}
	
}
