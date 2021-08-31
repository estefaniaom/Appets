package com.ceiba.cita.modelo.entidad;

import java.time.LocalDateTime;

import com.ceiba.cita.puerto.constantes.MensajeError;

import static com.ceiba.dominio.ValidadorArgumento.validarMenor;
import static com.ceiba.dominio.ValidadorArgumento.validarObligatorio;
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
	
	public Cita(LocalDateTime fecha, String servicio, Long idMascota) {
		
		validarObligatorio(fecha, MensajeError.SE_DEBE_INGRESAR_FECHA_CITA.getMensaje());
		validarMenor(LocalDateTime.now(), fecha, MensajeError.SE_DEBE_INGRESAR_MAYOR_FECHA_CITA.getMensaje());
		validarObligatorio(servicio, MensajeError.SE_DEBE_INGRESAR_SERVICIO_CITA.getMensaje());
		validarObligatorio(idMascota, MensajeError.SE_DEBE_INGRESAR_MASCOTA_CITA.getMensaje());
		
		this.fecha = fecha;
		this.servicio = servicio;
		this.idMascota = idMascota;
	}
}
