package com.ceiba.cita.servicio.testdatabuilder;

import java.time.LocalDateTime;

import com.ceiba.cita.modelo.dto.DtoCita;
import com.ceiba.cita.modelo.entidad.Cita;
import com.ceiba.mascota.modelo.dto.DtoMascota;

public class CitaTestDataBuilder {

	private Long id;
	private LocalDateTime fecha;
	private String servicio;
	private Long idMascota;
	private String nombre;
	private Long precio;
	
	private static final String SERVICIO_VACUNA_TRIPLEFELINA = "VACUNA TRIPLEFELINA";
	private static final String SERVICIO_VACUNA_RABIA = "VACUNA ANTIRABICA";
	private static final String SERVICIO_DESPARACITAR = "DESPARACITACION";
	private static final String SERVICIO_PELUQUERIA = "PELUQUERIA";
	
	public CitaTestDataBuilder(){
		id = Math.round(Math.random()*100);
		fecha = LocalDateTime.now();
		idMascota = Math.round(Math.random()*100);
		nombre = "Pantera";
	}
	
	public Cita build(){
		return new Cita(id,fecha,servicio,idMascota,nombre);
	}
	
	public Cita buildFueraHorario(){
		fecha = LocalDateTime.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonthValue(), LocalDateTime.now().getDayOfMonth(), 20, 0);
		return new Cita(id,fecha,servicio,idMascota,nombre);
	}
	
	public Cita buildServicioVacunaRabia(){
		servicio = SERVICIO_VACUNA_RABIA;
		return new Cita(id,fecha,servicio,idMascota,nombre);
	}
	
	public Cita buildServicioVacunaTripleFelina(){
		servicio = SERVICIO_VACUNA_TRIPLEFELINA;
		return new Cita(id,fecha,servicio,idMascota,nombre);
	}
	
	public Cita buildServicioPeluqueria(){
		servicio = SERVICIO_PELUQUERIA;
		return new Cita(id,fecha,servicio,idMascota,nombre);
	}
	
	public Cita buildServicioDesparacitacion(){
		servicio = SERVICIO_DESPARACITAR;
		return new Cita(id,fecha,servicio,idMascota,nombre);
	}
	
	public DtoMascota buildDtoMascota(){
		LocalDateTime fechaNacimiento = fecha.minusMonths(2);
		return new DtoMascota(idMascota, nombre, fechaNacimiento, "Jhon Doe", "3152000000", "Gato");
	}
	
	public DtoMascota buildDtoMascotaDescuento(){
		LocalDateTime fechaNacimiento = fecha;
		return new DtoMascota(idMascota, nombre, fechaNacimiento, "Jhon Doe", "3152000000", "Gato");
	}
	
	public DtoCita buildDtoCitaDesparacitacion(){
		LocalDateTime fechaAnterior = fecha.minusDays(5);
		servicio = SERVICIO_DESPARACITAR;
		return new DtoCita(id, fechaAnterior, servicio, idMascota, nombre, precio);
	}
	
	public DtoCita buildDtoCitaPeluqueria(){
		LocalDateTime fechaAnterior = fecha.minusMonths(2);
		servicio = SERVICIO_PELUQUERIA;
		return new DtoCita(id, fechaAnterior, servicio, idMascota, nombre, precio);
	}
	
	public DtoCita buildDtoCitaVacunaRabia(){
		LocalDateTime fechaAnterior = fecha.minusMonths(10);
		servicio = SERVICIO_VACUNA_RABIA;
		return new DtoCita(id, fechaAnterior, servicio, idMascota, nombre, precio);
	}
	
	public DtoCita buildDtoCitaVacunaTripleFelina(){
		LocalDateTime fechaAnterior = fecha.minusMonths(10);
		servicio = SERVICIO_VACUNA_TRIPLEFELINA;
		return new DtoCita(id, fechaAnterior, servicio, idMascota, nombre, precio);
	}
	
}
