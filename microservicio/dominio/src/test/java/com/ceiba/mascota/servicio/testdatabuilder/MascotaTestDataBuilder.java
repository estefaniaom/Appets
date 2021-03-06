package com.ceiba.mascota.servicio.testdatabuilder;

import java.time.LocalDateTime;

import com.ceiba.mascota.modelo.entidad.Mascota;

public class MascotaTestDataBuilder {

	private String nombre;
	private LocalDateTime fechaNacimiento;
	private String nombreContacto;
	private String telefono;
	private String tipo;
	
	public MascotaTestDataBuilder() {
        nombre = "Garfield";
        fechaNacimiento = LocalDateTime.now().minusYears(Math.round(Math.random()*10));
        nombreContacto = "Jhon Doe";
        telefono = "3152000000";
        tipo = "Gato";
    }
	
	public Mascota build(){
		Mascota mascota = new Mascota(nombre, fechaNacimiento, nombreContacto, telefono, tipo);
		mascota.getId();
		mascota.getNombre();
		mascota.getFechaNacimiento();
		mascota.getNombreContacto();
		mascota.getTelefono();
		mascota.getTipo();
		return mascota;
	}
	
}
