package com.ceiba.mascota.modelo.entidad;

import java.time.LocalDateTime;

import com.ceiba.cita.puerto.constantes.MensajeError;

import static com.ceiba.dominio.ValidadorArgumento.validarObligatorio;
import static com.ceiba.dominio.ValidadorArgumento.validarMenor;
import lombok.Getter;
@Getter
public class Mascota {

	private Long id;
	private String nombre;
	private LocalDateTime fechaNacimiento;
	private String nombreContacto;
	private String telefono;
	private String tipo;
	
	public Mascota(String nombre, LocalDateTime fechaNacimiento, String nombreContacto, String telefono, String tipo) {
		validarObligatorio(nombre,MensajeError.SE_DEBE_INGRESAR_NOMBRE.getMensaje());
		validarObligatorio(fechaNacimiento,MensajeError.SE_DEBE_INGRESAR_FECHA_NACIMIENTO.getMensaje());
		validarMenor(fechaNacimiento, LocalDateTime.now(),MensajeError.SE_DEBE_INGRESAR_FECHA_MENOR.getMensaje());
		validarObligatorio(nombreContacto,MensajeError.SE_DEBE_INGRESAR_CONTACTO.getMensaje());
		validarObligatorio(telefono,MensajeError.SE_DEBE_INGRESAR_TELEFONO.getMensaje());
		validarObligatorio(tipo,MensajeError.SE_DEBE_INGRESAR_TIPO.getMensaje());
		
		this.nombre = nombre;
		this.fechaNacimiento = fechaNacimiento;
		this.nombreContacto = nombreContacto;
		this.telefono = telefono;
		this.tipo = tipo;
	}
}
