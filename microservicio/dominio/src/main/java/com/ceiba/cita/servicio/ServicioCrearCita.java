package com.ceiba.cita.servicio;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import com.ceiba.cita.modelo.dto.DtoCita;
import com.ceiba.cita.modelo.entidad.Cita;
import com.ceiba.cita.puerto.constantes.MensajeError;
import com.ceiba.cita.puerto.constantes.ServiciosCita;
import com.ceiba.cita.puerto.repositorio.RepositorioCita;
import com.ceiba.dominio.excepcion.ExcepcionDuplicidad;
import com.ceiba.dominio.excepcion.ExcepcionValorInvalido;
import com.ceiba.mascota.modelo.dto.DtoMascota;

public class ServicioCrearCita {

	private static final int HORA_INICIAL = 7;
	private static final int HORA_FINAL = 18;
	private static final double DESCUENTO_CUMPLEANOS = 0.15;
	
	private final RepositorioCita repositorioCita;
	
	public ServicioCrearCita(RepositorioCita repositorioCita){
		this.repositorioCita = repositorioCita;
	}
	public Long ejecutar(Cita cita){
		validarExistenciaPrevia(cita);
		calcularPrecio(cita);
		validarHoraAtencion(cita);
		validarFecha(cita);
		return this.repositorioCita.crear(cita);
	}
	
	private void validarExistenciaPrevia(Cita cita){
		boolean existe = this.repositorioCita.existe(cita.getIdMascota(), cita.getFecha());
		if(existe) {
            throw new ExcepcionDuplicidad(MensajeError.LA_CITA_YA_EXISTE_EN_EL_SISTEMA.getMensaje());
        }
	}
	
	private void validarHoraAtencion(Cita cita){
		if(cita.getFecha().getHour() < HORA_INICIAL || cita.getFecha().getHour() > HORA_FINAL){
			throw new ExcepcionValorInvalido(MensajeError.LA_CITA_ESTA_FUERA_DE_HORARIO.getMensaje());
		}
	}
	
	private Cita calcularPrecio(Cita cita){
		Long precio;
		ServiciosCita[] listaServicios = ServiciosCita.values();
		Optional<ServiciosCita> servicioCita = Arrays.stream(listaServicios).filter(servicio -> servicio.getServicio().equals(cita.getServicio())).findFirst();
		if(servicioCita.isPresent()){
			precio = servicioCita.get().getPrecio();
		}else{
			throw new ExcepcionValorInvalido(MensajeError.EL_SERVICIO_NO_EXISTE.getMensaje());
		}
		DtoMascota mascota = encontrarMascota(cita.getIdMascota());
		if(mascota == null) {
			throw new ExcepcionValorInvalido(MensajeError.LA_MASCOTA_NO_EXISTE.getMensaje());
		}
		if(mascota.getFechaNacimiento().getMonthValue()==LocalDateTime.now().getMonthValue()){
			precio -=  Math.round(precio*DESCUENTO_CUMPLEANOS);
		}
		cita.setPrecio(precio);
		cita.setNombre(mascota.getNombre());
		return cita;
	}
	
	private DtoMascota encontrarMascota(Long id){
		return repositorioCita.encontrarPorId(id).isEmpty()?null:repositorioCita.encontrarPorId(id).get(0);
	}
	
	private void validarFecha(Cita cita){
		DtoCita dtoCita = repositorioCita.encontrarUltimaCitaPorMascotaYServicio(cita.getIdMascota(),cita.getServicio()).isEmpty()?null:repositorioCita.encontrarUltimaCitaPorMascotaYServicio(cita.getIdMascota(), cita.getServicio()).get(0);
		
		ServiciosCita[] listaServicios = ServiciosCita.values();
		Optional<ServiciosCita> servicioCita = Arrays.stream(listaServicios).filter(servicio -> servicio.getServicio().equals(cita.getServicio())).findFirst();
		if(dtoCita!=null && servicioCita.isPresent()){
			validarProximaFecha(dtoCita.getFecha(), cita.getFecha(), servicioCita.get().getFrecuencia());
		}
	}
	
	private void validarProximaFecha(LocalDateTime fechaAnterior, LocalDateTime fechaCita, int tiempoAdicional){
		LocalDateTime fechaProximoMes = fechaAnterior.plusMonths(tiempoAdicional);
		LocalDateTime fechaProxima = fechaProximoMes.minusDays(3);
		if(fechaCita.isBefore(fechaProxima)){
			throw new ExcepcionValorInvalido(MensajeError.LA_FECHA_ES_MENOR_A_PROXIMA_FECHA.getMensaje());
		}
	}
}
