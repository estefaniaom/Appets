package com.ceiba.mascota.comando;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ComandoMascota {

	private Long id;
	private String nombre;
	private LocalDateTime fecha;
	private String contacto;
	private String telefono;
	private String tipo;	
}
