package com.ceiba.mascota.consulta;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ceiba.mascota.modelo.dto.DtoMascota;
import com.ceiba.mascota.puerto.dao.DaoMascota;

@Component
public class ManejadorListarMascotas {

	private final DaoMascota daoMascota;
	
	public ManejadorListarMascotas(DaoMascota daoMascota){
		this.daoMascota = daoMascota;
	}
	
	public List<DtoMascota> ejecutar(){
		return this.daoMascota.listar();
	}
	
	public DtoMascota encontrarPorId(Long id){
		return this.daoMascota.encontrarPorId(id);
	}
	
	
}
