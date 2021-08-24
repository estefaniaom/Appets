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
	private static final String SERVICIO_NO_EXISTE = "OTRO SERVICIO";
	
	public CitaTestDataBuilder(){
		id = Math.round(Math.random()*100);
		fecha = LocalDateTime.now();
		idMascota = Math.round(Math.random()*100);
		nombre = "Pantera";
	}
	
	public Cita build(){
		return new Cita(id,fecha,servicio,idMascota,nombre);
	}
	
	public Cita buildFueraHorarioDespues(){
		fecha = LocalDateTime.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonthValue(), LocalDateTime.now().getDayOfMonth(), 20, 0);
		return new Cita(id,fecha,servicio,idMascota,nombre);
	}
	
	public Cita buildFueraHorarioAntes(){
		fecha = LocalDateTime.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonthValue(), LocalDateTime.now().getDayOfMonth(), 5, 0);
		return new Cita(id,fecha,servicio,idMascota,nombre);
	}
	
	public Cita buildFueraHorarioDentro(){
		fecha = LocalDateTime.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonthValue(), LocalDateTime.now().getDayOfMonth(), 9, 0);
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
	
	public Cita buildServicioNoExiste(){
		servicio = SERVICIO_NO_EXISTE;
		Cita cita = new Cita(id,fecha,servicio,idMascota,nombre);
		cita.setId(cita.getId());
		cita.setNombre(cita.getNombre());
		return cita;
	}
	
	public DtoMascota buildDtoMascota(){
		LocalDateTime fechaNacimiento = fecha.minusMonths(2);
		DtoMascota dtoMascota = new DtoMascota(idMascota, nombre, fechaNacimiento, "Jhon Doe", "3152000000", "Gato", fecha, fecha, fecha, fecha);
		dtoMascota.setId(dtoMascota.getId());
		dtoMascota.setNombre(dtoMascota.getNombre());
		dtoMascota.setFechaNacimiento(dtoMascota.getFechaNacimiento());
		dtoMascota.setNombreContacto(dtoMascota.getNombreContacto());
		dtoMascota.setTelefono(dtoMascota.getTelefono());
		dtoMascota.setTipo(dtoMascota.getTipo());
		dtoMascota.setFechaProximaDesparacitacion(dtoMascota.getFechaProximaDesparacitacion());
		dtoMascota.setFechaProximaPeluqueria(dtoMascota.getFechaProximaPeluqueria());
		dtoMascota.setFechaProximaVacunaRabia(dtoMascota.getFechaProximaVacunaRabia());
		dtoMascota.setFechaProximaVacunaTripleFelina(dtoMascota.getFechaProximaVacunaTripleFelina());
		return dtoMascota;
		
	}
	
	public DtoMascota buildDtoMascotaDescuento(){
		LocalDateTime fechaNacimiento = fecha;
		return new DtoMascota(idMascota, nombre, fechaNacimiento, "Jhon Doe", "3152000000", "Gato");
	}
	
	public DtoCita buildDtoCitaDesparacitacion(){
		LocalDateTime fechaAnterior = fecha.minusDays(5);
		servicio = SERVICIO_DESPARACITAR;
		DtoCita dtoCita = new DtoCita(id, fechaAnterior, servicio, idMascota, nombre, precio);
		dtoCita.getFecha();
		dtoCita.getId();
		dtoCita.getIdMascota();
		dtoCita.getNombre();
		dtoCita.getPrecio();
		dtoCita.getServicio();
		return dtoCita;
	}
	
	public DtoCita buildDtoCitaPeluqueria(){
		LocalDateTime fechaAnterior = fecha.minusMonths(2);
		servicio = SERVICIO_PELUQUERIA;
		return new DtoCita(id, fechaAnterior, servicio, idMascota, nombre, precio);
	}
	
	public DtoCita buildDtoCitaPeluqueriaCorrecta(){
		LocalDateTime fechaAnterior = fecha.minusMonths(3);
		servicio = SERVICIO_PELUQUERIA;
		return new DtoCita(id, fechaAnterior, servicio, idMascota, nombre, precio);
	}
	
	public DtoCita buildDtoCitaDesparacitacionCorrecta(){
		LocalDateTime fechaAnterior = fecha.minusMonths(1);
		servicio = SERVICIO_DESPARACITAR;
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
