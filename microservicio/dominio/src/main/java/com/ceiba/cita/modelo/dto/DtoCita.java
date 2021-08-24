package com.ceiba.cita.modelo.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DtoCita {

	private Long id;
	private LocalDateTime fecha;
	private String servicio;
	private Long idMascota;
	private String nombre;
	private Long precio;
	
}
