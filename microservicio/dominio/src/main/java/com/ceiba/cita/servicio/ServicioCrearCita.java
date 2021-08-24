package com.ceiba.cita.servicio;

import java.time.LocalDateTime;

import com.ceiba.cita.modelo.dto.DtoCita;
import com.ceiba.cita.modelo.entidad.Cita;
import com.ceiba.cita.puerto.repositorio.RepositorioCita;
import com.ceiba.dominio.excepcion.ExcepcionDuplicidad;
import com.ceiba.dominio.excepcion.ExcepcionValorInvalido;
import com.ceiba.mascota.modelo.dto.DtoMascota;

public class ServicioCrearCita {

	private static final int HORA_INICIAL = 7;
	private static final int HORA_FINAL = 18;
	private static final long PRECIO_PELUQUERIA = 45000;
	private static final long PRECIO_VACUNA_TRIPLEFELINA = 35000;
	private static final long PRECIO_VACUNA_RABIA = 35000;
	private static final long PRECIO_DESPARACITACION = 60000;
	private static final String SERVICIO_VACUNA_TRIPLEFELINA = "VACUNA TRIPLEFELINA";
	private static final String SERVICIO_VACUNA_RABIA = "VACUNA ANTIRABICA";
	private static final String SERVICIO_DESPARACITAR = "DESPARACITACION";
	private static final String SERVICIO_PELUQUERIA = "PELUQUERIA";
	private static final double DESCUENTO_CUMPLEANOS = 0.15;
	private static final int FRECUENCIA_MESES_VACUNAS = 12;
	private static final int FRECUENCIA_MESES_DESPARACITAR = 1;
	private static final int FRECUENCIA_MESES_PELUQUERIA = 3;
	
	private static final String LA_CITA_YA_EXISTE_EN_EL_SISTEMA = "La mascota ya tiene una cita en este horario";
	private static final String LA_CITA_ESTA_FUERA_DE_HORARIO = "El horario de atencion es desde las 07:00 hasta las 19:00";
	private static final String EL_SERVICIO_NO_EXISTE = "El servicio es desconocido";
	private static final String LA_MASCOTA_NO_EXISTE = "La mascota no esta registrada en el sistema";
	private static final String LA_FECHA_ES_MENOR_A_PROXIMA_FECHA = "No se cumple la fecha minima entre citas del mismo servicio";
	
	private final RepositorioCita repositorioCita;
	
	public ServicioCrearCita(RepositorioCita repositorioCita){
		this.repositorioCita = repositorioCita;
	}
	public Long ejecutar(Cita cita){
		validarExistenciaPrevia(cita);
		cita = calcularPrecio(cita);
		validarHoraAtencion(cita);
		validarFecha(cita);
		return this.repositorioCita.crear(cita);
	}
	
	public void validarExistenciaPrevia(Cita cita){
		boolean existe = this.repositorioCita.existe(cita.getIdMascota(), cita.getFecha());
		if(existe) {
            throw new ExcepcionDuplicidad(LA_CITA_YA_EXISTE_EN_EL_SISTEMA);
        }
	}
	
	public void validarHoraAtencion(Cita cita){
		if(cita.getFecha().getHour() < HORA_INICIAL || cita.getFecha().getHour() > HORA_FINAL){
			throw new ExcepcionValorInvalido(LA_CITA_ESTA_FUERA_DE_HORARIO);
		}
	}
	
	public Cita calcularPrecio(Cita cita){
		Long precio;
		if(cita.getServicio().equals(SERVICIO_VACUNA_TRIPLEFELINA)){
			precio = PRECIO_VACUNA_TRIPLEFELINA;
		}else if (cita.getServicio().equals(SERVICIO_VACUNA_RABIA)){
			precio = PRECIO_VACUNA_RABIA;
		}else if (cita.getServicio().equals(SERVICIO_DESPARACITAR)){
			precio = PRECIO_DESPARACITACION;
		}else if (cita.getServicio().equals(SERVICIO_PELUQUERIA)){
			precio = PRECIO_PELUQUERIA;
		}else{
			throw new ExcepcionValorInvalido(EL_SERVICIO_NO_EXISTE);
		}
		DtoMascota mascota = encontrarMascota(cita.getIdMascota());
		if(mascota == null) {
			throw new ExcepcionValorInvalido(LA_MASCOTA_NO_EXISTE);
		}
		if(mascota.getFechaNacimiento().getMonthValue()==LocalDateTime.now().getMonthValue()){
			long descuento = Math.round(precio*DESCUENTO_CUMPLEANOS);
			precio -= descuento;
		}
		cita.setPrecio(precio);
		cita.setNombre(mascota.getNombre());
		return cita;
	}
	
	public DtoMascota encontrarMascota(Long id){
		return repositorioCita.encontrarPorId(id).isEmpty()?null:repositorioCita.encontrarPorId(id).get(0);
	}
	
	public void validarFecha(Cita cita){
		DtoCita dtoCita = repositorioCita.encontrarUltimaCitaPorMascotaYServicio(cita.getIdMascota(),cita.getServicio()).isEmpty()?null:repositorioCita.encontrarUltimaCitaPorMascotaYServicio(cita.getIdMascota(), cita.getServicio()).get(0);
		if(dtoCita!=null && cita.getServicio().equals(SERVICIO_DESPARACITAR)){
			validarProximaFecha(dtoCita.getFecha(), cita.getFecha(), FRECUENCIA_MESES_DESPARACITAR);
		}else if(dtoCita!=null && cita.getServicio().equals(SERVICIO_PELUQUERIA)){
			validarProximaFecha(dtoCita.getFecha(), cita.getFecha(), FRECUENCIA_MESES_PELUQUERIA);
		}else if(dtoCita!=null && cita.getServicio().equals(SERVICIO_VACUNA_TRIPLEFELINA)){
			validarProximaFecha(dtoCita.getFecha(), cita.getFecha(), FRECUENCIA_MESES_VACUNAS);
		}else if(dtoCita!=null && cita.getServicio().equals(SERVICIO_VACUNA_RABIA)){
			validarProximaFecha(dtoCita.getFecha(), cita.getFecha(), FRECUENCIA_MESES_VACUNAS);
		}
	}
	
	public void validarProximaFecha(LocalDateTime fechaAnterior, LocalDateTime fechaCita, int tiempoAdicional){
		LocalDateTime fechaProximoMes = fechaAnterior.plusMonths(tiempoAdicional);
		LocalDateTime fechaProxima = fechaProximoMes.minusDays(3);
		if(fechaCita.isBefore(fechaProxima)){
			throw new ExcepcionValorInvalido(LA_FECHA_ES_MENOR_A_PROXIMA_FECHA);
		}
	}
}
