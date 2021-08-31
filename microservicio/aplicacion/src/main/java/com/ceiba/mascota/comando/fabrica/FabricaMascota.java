package com.ceiba.mascota.comando.fabrica;

import org.springframework.stereotype.Component;

import com.ceiba.mascota.comando.ComandoMascota;
import com.ceiba.mascota.modelo.entidad.Mascota;

@Component
public class FabricaMascota {

	public Mascota crearMascota(ComandoMascota comandoMascota){
		return new Mascota(
				comandoMascota.getNombre(), 
				comandoMascota.getFecha(), 
				comandoMascota.getContacto(), 
				comandoMascota.getTelefono(), 
				comandoMascota.getTipo()
		);
	}
}
