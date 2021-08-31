package com.ceiba.mascota.servicio;

import com.ceiba.cita.puerto.constantes.MensajeError;
import com.ceiba.dominio.excepcion.ExcepcionDuplicidad;
import com.ceiba.mascota.modelo.entidad.Mascota;
import com.ceiba.mascota.puerto.repositorio.RepositorioMascota;

public class ServicioCrearMascota {

	private final RepositorioMascota repositorioMascota;
	
	public ServicioCrearMascota(RepositorioMascota repositorioMascota){
		this.repositorioMascota = repositorioMascota;
	}
	
	public Long ejecutar(Mascota mascota){
		validarExistenciaPrevia(mascota);
		return this.repositorioMascota.crear(mascota);
	}
	
	private void validarExistenciaPrevia(Mascota mascota){
		boolean existe = this.repositorioMascota.existe(mascota.getNombre(), mascota.getNombreContacto());
		if(existe) { 
			throw new ExcepcionDuplicidad(MensajeError.LA_MASCOTA_YA_EXISTE_EN_EL_SISTEMA.getMensaje()); 
		}
	}
}
