package com.ceiba.cita.puerto.constantes;

import lombok.Getter;

@Getter
public enum ServiciosCita {

	SERVICIO_VACUNA_TRIPLEFELINA("VACUNA TRIPLEFELINA", 12, 35000),
	SERVICIO_VACUNA_RABIA("VACUNA ANTIRABICA", 12, 35000),
	SERVICIO_DESPARACITAR("DESPARACITACION", 1, 60000),
	SERVICIO_PELUQUERIA("PELUQUERIA", 3, 45000);
	
	private final String servicio;
	private final int frecuencia;
	private final long precio;
	
	ServiciosCita(String servicio, int frecuencia, long precio){
		this.servicio = servicio;
		this.frecuencia = frecuencia;
		this.precio = precio;
	}
	
}
