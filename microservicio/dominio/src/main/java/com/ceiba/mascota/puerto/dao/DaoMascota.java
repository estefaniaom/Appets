package com.ceiba.mascota.puerto.dao;

import java.util.List;

import com.ceiba.cita.modelo.dto.DtoCita;
import com.ceiba.mascota.modelo.dto.DtoMascota;

public interface DaoMascota {

	List<DtoMascota> listar();
	DtoMascota encontrarPorId(Long id);
	List<DtoCita> encontrarUltimaCitaPorMascotaYServicio(Long idMascota, String servicio);
}
