package com.ceiba.cita.puerto.repositorio;

import java.time.LocalDateTime;
import java.util.List;

import com.ceiba.cita.modelo.dto.DtoCita;
import com.ceiba.cita.modelo.entidad.Cita;
import com.ceiba.mascota.modelo.dto.DtoMascota;

public interface RepositorioCita {

	Long crear(Cita cita);
	void actualizar(Cita cita);
	void eliminar(Long id);
	boolean existe(Long idMascota, LocalDateTime fecha);
	List<DtoMascota> encontrarPorId(Long id);
	List<DtoCita> encontrarUltimaCitaPorMascotaYServicio(Long idMascota, String servicio);
}
