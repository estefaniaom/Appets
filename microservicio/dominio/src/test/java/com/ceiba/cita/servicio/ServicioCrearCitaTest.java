package com.ceiba.cita.servicio;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

import com.ceiba.BasePrueba;
import com.ceiba.cita.modelo.dto.DtoCita;
import com.ceiba.cita.modelo.entidad.Cita;
import com.ceiba.cita.puerto.repositorio.RepositorioCita;
import com.ceiba.cita.servicio.testdatabuilder.CitaTestDataBuilder;
import com.ceiba.dominio.excepcion.ExcepcionDuplicidad;
import com.ceiba.dominio.excepcion.ExcepcionValorInvalido;
import com.ceiba.mascota.modelo.dto.DtoMascota;

public class ServicioCrearCitaTest {
	
	private static final long PRECIO_PELUQUERIA = 45000;
	private static final long PRECIO_VACUNA_TRIPLEFELINA = 35000;
	private static final long PRECIO_VACUNA_RABIA = 35000;
	private static final long PRECIO_DESPARACITACION = 60000;
	private static final double DESCUENTO_CUMPLEANOS = 0.15;

	@Test
	public void validarCitaExistenciaPrevia(){
		Cita cita = new CitaTestDataBuilder().build();
		RepositorioCita repositorioCita = Mockito.mock(RepositorioCita.class);
		ServicioCrearCita servicioCrearCita = new ServicioCrearCita(repositorioCita);
		Mockito.when(repositorioCita.existe(cita.getIdMascota(), cita.getFecha())).thenReturn(true);
		BasePrueba.assertThrows(() -> servicioCrearCita.ejecutar(cita), ExcepcionDuplicidad.class,"La mascota ya tiene una cita en este horario");
	}
	
	@Test
	public void validarCitaHoraAtencion(){
		Cita cita = new CitaTestDataBuilder().buildFueraHorario();
		RepositorioCita repositorioCita = Mockito.mock(RepositorioCita.class);
		ServicioCrearCita servicioCrearCita = new ServicioCrearCita(repositorioCita);
		BasePrueba.assertThrows(() -> servicioCrearCita.validarHoraAtencion(cita), ExcepcionValorInvalido.class,"El horario de atencion es desde las 07:00 hasta las 19:00");
	}
	
	@Test
	public void validarCitaCalcularPrecio(){
		Cita citaDesparacitacion = new CitaTestDataBuilder().buildServicioDesparacitacion();
		Cita citaPeluqueria = new CitaTestDataBuilder().buildServicioPeluqueria();
		Cita citaVacunaRabia = new CitaTestDataBuilder().buildServicioVacunaRabia();
		Cita citaVacunaTripleFelina = new CitaTestDataBuilder().buildServicioVacunaTripleFelina();
		RepositorioCita repositorioCita = Mockito.mock(RepositorioCita.class);
		ServicioCrearCita servicioCrearCita = new ServicioCrearCita(repositorioCita);
		List<DtoMascota> mascotas = new ArrayList<>();
		DtoMascota dtoMascota = new CitaTestDataBuilder().buildDtoMascota();
		mascotas.add(dtoMascota);
		Mockito.when(repositorioCita.encontrarPorId(citaDesparacitacion.getIdMascota())).thenReturn(mascotas);
		Mockito.when(repositorioCita.encontrarPorId(citaPeluqueria.getIdMascota())).thenReturn(mascotas);
		Mockito.when(repositorioCita.encontrarPorId(citaVacunaRabia.getIdMascota())).thenReturn(mascotas);
		Mockito.when(repositorioCita.encontrarPorId(citaVacunaTripleFelina.getIdMascota())).thenReturn(mascotas);
		Cita citaDesparacitacionPrecio  = servicioCrearCita.calcularPrecio(citaDesparacitacion);
		Cita citaPeluqueriaPrecio  = servicioCrearCita.calcularPrecio(citaPeluqueria);
		Cita citaVacunaRabiaPrecio  = servicioCrearCita.calcularPrecio(citaVacunaRabia);
		Cita citaVacunaTripleFelinaPrecio  = servicioCrearCita.calcularPrecio(citaVacunaTripleFelina);
		assertEquals(citaDesparacitacionPrecio.getPrecio().longValue(), PRECIO_DESPARACITACION);
		assertEquals(citaPeluqueriaPrecio.getPrecio().longValue(), PRECIO_PELUQUERIA);
		assertEquals(citaVacunaRabiaPrecio.getPrecio().longValue(), PRECIO_VACUNA_RABIA);
		assertEquals(citaVacunaTripleFelinaPrecio.getPrecio().longValue(), PRECIO_VACUNA_TRIPLEFELINA);
	}
	
	@Test
	public void validarMascotaNoExiste(){
		Cita citaDesparacitacion = new CitaTestDataBuilder().buildServicioDesparacitacion();
		RepositorioCita repositorioCita = Mockito.mock(RepositorioCita.class);
		ServicioCrearCita servicioCrearCita = new ServicioCrearCita(repositorioCita);
		BasePrueba.assertThrows(() -> servicioCrearCita.calcularPrecio(citaDesparacitacion), ExcepcionValorInvalido.class,"La mascota no esta registrada en el sistema");
	}
	
	@Test
	public void validarCitaCalcularPrecioDescuento(){
		Cita citaDesparacitacion = new CitaTestDataBuilder().buildServicioDesparacitacion();
		Cita citaPeluqueria = new CitaTestDataBuilder().buildServicioPeluqueria();
		Cita citaVacunaRabia = new CitaTestDataBuilder().buildServicioVacunaRabia();
		Cita citaVacunaTripleFelina = new CitaTestDataBuilder().buildServicioVacunaTripleFelina();
		RepositorioCita repositorioCita = Mockito.mock(RepositorioCita.class);
		ServicioCrearCita servicioCrearCita = new ServicioCrearCita(repositorioCita);
		List<DtoMascota> mascotas = new ArrayList<>();
		DtoMascota dtoMascota = new CitaTestDataBuilder().buildDtoMascotaDescuento();
		mascotas.add(dtoMascota);
		Mockito.when(repositorioCita.encontrarPorId(citaDesparacitacion.getIdMascota())).thenReturn(mascotas);
		Mockito.when(repositorioCita.encontrarPorId(citaPeluqueria.getIdMascota())).thenReturn(mascotas);
		Mockito.when(repositorioCita.encontrarPorId(citaVacunaRabia.getIdMascota())).thenReturn(mascotas);
		Mockito.when(repositorioCita.encontrarPorId(citaVacunaTripleFelina.getIdMascota())).thenReturn(mascotas);
		Cita citaDesparacitacionPrecio  = servicioCrearCita.calcularPrecio(citaDesparacitacion);
		Cita citaPeluqueriaPrecio  = servicioCrearCita.calcularPrecio(citaPeluqueria);
		Cita citaVacunaRabiaPrecio  = servicioCrearCita.calcularPrecio(citaVacunaRabia);
		Cita citaVacunaTripleFelinaPrecio  = servicioCrearCita.calcularPrecio(citaVacunaTripleFelina);
		assertEquals(citaDesparacitacionPrecio.getPrecio().longValue(), PRECIO_DESPARACITACION-Math.round(PRECIO_DESPARACITACION*DESCUENTO_CUMPLEANOS));
		assertEquals(citaPeluqueriaPrecio.getPrecio().longValue(), PRECIO_PELUQUERIA-Math.round(PRECIO_PELUQUERIA*DESCUENTO_CUMPLEANOS));
		assertEquals(citaVacunaRabiaPrecio.getPrecio().longValue(), PRECIO_VACUNA_RABIA-Math.round(PRECIO_VACUNA_RABIA*DESCUENTO_CUMPLEANOS));
		assertEquals(citaVacunaTripleFelinaPrecio.getPrecio().longValue(), PRECIO_VACUNA_TRIPLEFELINA-Math.round(PRECIO_VACUNA_TRIPLEFELINA*DESCUENTO_CUMPLEANOS));
	}
	
	@Test
	public void validarCitaFecha(){
		Cita citaDesparacitacion = new CitaTestDataBuilder().buildServicioDesparacitacion();
		Cita citaPeluqueria = new CitaTestDataBuilder().buildServicioPeluqueria();
		Cita citaVacunaRabia = new CitaTestDataBuilder().buildServicioVacunaRabia();
		Cita citaVacunaTripleFelina = new CitaTestDataBuilder().buildServicioVacunaTripleFelina();
		RepositorioCita repositorioCita = Mockito.mock(RepositorioCita.class);
		ServicioCrearCita servicioCrearCita = new ServicioCrearCita(repositorioCita);
		List<DtoCita> citasDesparacitacion = new ArrayList<>();
		DtoCita ultimaCitaDesparacitacion = new CitaTestDataBuilder().buildDtoCitaDesparacitacion();
		citasDesparacitacion.add(ultimaCitaDesparacitacion);
		Mockito.when(repositorioCita.encontrarUltimaCitaPorMascotaYServicio(citaDesparacitacion.getIdMascota(), citaDesparacitacion.getServicio())).thenReturn(citasDesparacitacion);
		List<DtoCita> citasPeluqueria = new ArrayList<>();
		DtoCita ultimaCitaPeluqueria = new CitaTestDataBuilder().buildDtoCitaPeluqueria();
		citasPeluqueria.add(ultimaCitaPeluqueria);
		Mockito.when(repositorioCita.encontrarUltimaCitaPorMascotaYServicio(citaPeluqueria.getIdMascota(), citaPeluqueria.getServicio())).thenReturn(citasPeluqueria);
		List<DtoCita> citasVacunaRabia = new ArrayList<>();
		DtoCita ultimaCitaVacunaRabia = new CitaTestDataBuilder().buildDtoCitaVacunaRabia();
		citasVacunaRabia.add(ultimaCitaVacunaRabia);
		Mockito.when(repositorioCita.encontrarUltimaCitaPorMascotaYServicio(citaVacunaRabia.getIdMascota(), citaVacunaRabia.getServicio())).thenReturn(citasVacunaRabia);
		List<DtoCita> citasVacunaTripleFelina = new ArrayList<>();
		DtoCita ultimaCitaVacunaTripleFelina = new CitaTestDataBuilder().buildDtoCitaVacunaTripleFelina();
		citasVacunaTripleFelina.add(ultimaCitaVacunaTripleFelina);
		Mockito.when(repositorioCita.encontrarUltimaCitaPorMascotaYServicio(citaVacunaTripleFelina.getIdMascota(), citaVacunaTripleFelina.getServicio())).thenReturn(citasVacunaTripleFelina);
		BasePrueba.assertThrows(() -> servicioCrearCita.validarFecha(citaDesparacitacion), ExcepcionValorInvalido.class,"No se cumple la fecha minima entre citas del mismo servicio");
		BasePrueba.assertThrows(() -> servicioCrearCita.validarFecha(citaPeluqueria), ExcepcionValorInvalido.class,"No se cumple la fecha minima entre citas del mismo servicio");
		BasePrueba.assertThrows(() -> servicioCrearCita.validarFecha(citaVacunaRabia), ExcepcionValorInvalido.class,"No se cumple la fecha minima entre citas del mismo servicio");
		BasePrueba.assertThrows(() -> servicioCrearCita.validarFecha(citaVacunaTripleFelina), ExcepcionValorInvalido.class,"No se cumple la fecha minima entre citas del mismo servicio");
	}
	
}
