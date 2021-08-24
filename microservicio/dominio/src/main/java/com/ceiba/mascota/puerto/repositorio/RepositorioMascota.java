package com.ceiba.mascota.puerto.repositorio;

import com.ceiba.mascota.modelo.entidad.Mascota;

public interface RepositorioMascota {

	Long crear(Mascota mascota);
	void actualizar(Mascota mascota);
	void eliminar(Long id);
	boolean existe(String nombre, String nombreContacto);
	
}
