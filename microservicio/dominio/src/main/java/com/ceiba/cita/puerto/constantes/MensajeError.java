package com.ceiba.cita.puerto.constantes;

import lombok.Getter;

@Getter
public enum MensajeError {

	SE_DEBE_INGRESAR_FECHA_CITA("Se debe ingresar la fecha de la cita"),
	SE_DEBE_INGRESAR_MAYOR_FECHA_CITA("La fecha de la cita debe ser mayor a la actual"),
	SE_DEBE_INGRESAR_SERVICIO_CITA("Se debe ingresar el servicio de la cita"),
	SE_DEBE_INGRESAR_MASCOTA_CITA("Se debe ingresar la mascota de la cita"),
	
	SE_DEBE_INGRESAR_NOMBRE("Se debe ingresar el nombre de la mascota"),
	SE_DEBE_INGRESAR_FECHA_NACIMIENTO("Se debe ingresar la fecha de nacimiento de la mascota"),
	SE_DEBE_INGRESAR_FECHA_MENOR("La fecha de nacimiento de la mascota debe ser menor a la fecha actual"),
	SE_DEBE_INGRESAR_CONTACTO("Se debe ingresar el nombre del contacto"),
	SE_DEBE_INGRESAR_TELEFONO("Se debe ingresar el telefono"),
	SE_DEBE_INGRESAR_TIPO("Se debe ingresar el tipo de la mascota"),
	
	LA_CITA_YA_EXISTE_EN_EL_SISTEMA("La mascota ya tiene una cita en este horario"),
	LA_CITA_ESTA_FUERA_DE_HORARIO("El horario de atencion es desde las 07:00 hasta las 19:00"),
	EL_SERVICIO_NO_EXISTE("El servicio es desconocido"),
	LA_MASCOTA_NO_EXISTE("La mascota no esta registrada en el sistema"),
	LA_FECHA_ES_MENOR_A_PROXIMA_FECHA("No se cumple la fecha minima entre citas del mismo servicio"),
	
	LA_MASCOTA_YA_EXISTE_EN_EL_SISTEMA("La mascota ya existe en el sistema");
	
	private String mensaje;
	
	MensajeError(String mensaje){
		this.mensaje = mensaje;
	}
	
}
