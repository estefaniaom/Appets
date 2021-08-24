package com.ceiba.mascota.consulta;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ceiba.cita.modelo.dto.DtoCita;
import com.ceiba.dominio.excepcion.ExcepcionSinDatos;
import com.ceiba.mascota.modelo.dto.DtoMascota;
import com.ceiba.mascota.puerto.dao.DaoMascota;

@Component
public class ManejadorListarMascotas {

	private final DaoMascota daoMascota;
	
	private static final String SERVICIO_VACUNA_TRIPLEFELINA = "VACUNA TRIPLEFELINA";
	private static final String SERVICIO_VACUNA_RABIA = "VACUNA ANTIRABICA";
	private static final String SERVICIO_DESPARACITAR = "DESPARACITACION";
	private static final String SERVICIO_PELUQUERIA = "PELUQUERIA";
	private static final int FRECUENCIA_MESES_VACUNAS = 12;
	private static final int FRECUENCIA_MESES_DESPARACITAR = 1;
	private static final int FRECUENCIA_MESES_PELUQUERIA = 3;
	
	private static final String LA_MASCOTA_NO_EXISTE = "No hay registrado en el sistema ninguna mascota con ese id";
	
	public ManejadorListarMascotas(DaoMascota daoMascota){
		this.daoMascota = daoMascota;
	}
	
	public List<DtoMascota> ejecutar(){
		return this.daoMascota.listar();
	}
	
	public DtoMascota encontrarPorId(Long id){
		DtoMascota dtoMascota = this.daoMascota.encontrarPorId(id);
		if(dtoMascota==null){
			throw new ExcepcionSinDatos(LA_MASCOTA_NO_EXISTE);
		}
		dtoMascota = completarProximasFechas(dtoMascota,SERVICIO_DESPARACITAR);
		dtoMascota = completarProximasFechas(dtoMascota,SERVICIO_PELUQUERIA);
		dtoMascota = completarProximasFechas(dtoMascota,SERVICIO_VACUNA_TRIPLEFELINA);
		dtoMascota = completarProximasFechas(dtoMascota,SERVICIO_VACUNA_RABIA);
		return dtoMascota;
	}
	
	public DtoMascota completarProximasFechas(DtoMascota dtoMascota, String servicio){
		DtoCita dtoCita = this.daoMascota.encontrarUltimaCitaPorMascotaYServicio(dtoMascota.getId(), servicio).isEmpty()?null:this.daoMascota.encontrarUltimaCitaPorMascotaYServicio(dtoMascota.getId(), servicio).get(0);
		if(dtoCita!=null && dtoCita.getServicio().equals(SERVICIO_DESPARACITAR)){
			dtoMascota.setFechaProximaDesparacitacion(calcularProximaFecha(dtoCita.getFecha(), FRECUENCIA_MESES_DESPARACITAR));
		}else if(dtoCita!=null && dtoCita.getServicio().equals(SERVICIO_PELUQUERIA)){
			dtoMascota.setFechaProximaPeluqueria(calcularProximaFecha(dtoCita.getFecha(), FRECUENCIA_MESES_PELUQUERIA));
		}else if(dtoCita!=null && dtoCita.getServicio().equals(SERVICIO_VACUNA_TRIPLEFELINA)){
			dtoMascota.setFechaProximaVacunaTripleFelina(calcularProximaFecha(dtoCita.getFecha(), FRECUENCIA_MESES_VACUNAS));
		}else if(dtoCita!=null && dtoCita.getServicio().equals(SERVICIO_VACUNA_RABIA)){
			dtoMascota.setFechaProximaVacunaRabia(calcularProximaFecha(dtoCita.getFecha(), FRECUENCIA_MESES_VACUNAS));
		}
		return dtoMascota;
	}
	
	public LocalDateTime calcularProximaFecha(LocalDateTime fechaAnterior, int tiempoAdicional){
		LocalDateTime fechaProximoMes = fechaAnterior.plusMonths(tiempoAdicional);
		return fechaProximoMes.minusDays(3);
		
	}
}
