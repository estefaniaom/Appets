package com.ceiba.cita.servicio;

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
		Cita citaDespues = new CitaTestDataBuilder().buildFueraHorarioDespues();
		Cita citaAntes = new CitaTestDataBuilder().buildFueraHorarioAntes();
		Cita citaDentro = new CitaTestDataBuilder().buildFueraHorarioDentro();
		RepositorioCita repositorioCita = Mockito.mock(RepositorioCita.class);
		ServicioCrearCita servicioCrearCita = new ServicioCrearCita(repositorioCita);
		List<DtoMascota> mascotas = new ArrayList<>();
		DtoMascota dtoMascota = new CitaTestDataBuilder().buildDtoMascota();
		mascotas.add(dtoMascota);
		Mockito.when(repositorioCita.encontrarPorId(citaDespues.getIdMascota())).thenReturn(mascotas);
		BasePrueba.assertThrows(() -> servicioCrearCita.ejecutar(citaDespues), ExcepcionValorInvalido.class,"El horario de atencion es desde las 07:00 hasta las 19:00");
		Mockito.when(repositorioCita.encontrarPorId(citaAntes.getIdMascota())).thenReturn(mascotas);
		BasePrueba.assertThrows(() -> servicioCrearCita.ejecutar(citaAntes), ExcepcionValorInvalido.class,"El horario de atencion es desde las 07:00 hasta las 19:00");
		Mockito.when(repositorioCita.encontrarPorId(citaDentro.getIdMascota())).thenReturn(mascotas);
		BasePrueba.assertNoThrows(() -> servicioCrearCita.ejecutar(citaDentro));
	}
	@Test
	public void validarCitaCalcularPrecio(){
		Cita citaDesparacitacion = new CitaTestDataBuilder().buildServicioDesparacitacion();
		Cita citaPeluqueria = new CitaTestDataBuilder().buildServicioPeluqueria();
		Cita citaVacunaRabia = new CitaTestDataBuilder().buildServicioVacunaRabia();
		Cita citaVacunaTripleFelina = new CitaTestDataBuilder().buildServicioVacunaTripleFelina();
		Cita citaServicioNoExiste = new CitaTestDataBuilder().buildServicioNoExiste();
		RepositorioCita repositorioCita = Mockito.mock(RepositorioCita.class);
		ServicioCrearCita servicioCrearCita = new ServicioCrearCita(repositorioCita);
		List<DtoMascota> mascotas = new ArrayList<>();
		DtoMascota dtoMascota = new CitaTestDataBuilder().buildDtoMascota();
		mascotas.add(dtoMascota);
		Mockito.when(repositorioCita.encontrarPorId(citaDesparacitacion.getIdMascota())).thenReturn(mascotas);
		BasePrueba.assertNoThrows(() -> servicioCrearCita.ejecutar(citaDesparacitacion));
		Mockito.when(repositorioCita.encontrarPorId(citaPeluqueria.getIdMascota())).thenReturn(mascotas);
		BasePrueba.assertNoThrows(() -> servicioCrearCita.ejecutar(citaPeluqueria));
		Mockito.when(repositorioCita.encontrarPorId(citaVacunaRabia.getIdMascota())).thenReturn(mascotas);
		BasePrueba.assertNoThrows(() -> servicioCrearCita.ejecutar(citaVacunaRabia));
		Mockito.when(repositorioCita.encontrarPorId(citaVacunaTripleFelina.getIdMascota())).thenReturn(mascotas);
		BasePrueba.assertNoThrows(() -> servicioCrearCita.ejecutar(citaVacunaTripleFelina));
		BasePrueba.assertThrows(() -> servicioCrearCita.ejecutar(citaServicioNoExiste), ExcepcionValorInvalido.class,"El servicio es desconocido");
	}
	@Test
	public void validarMascotaNoExiste(){
		Cita citaDesparacitacion = new CitaTestDataBuilder().buildServicioDesparacitacion();
		RepositorioCita repositorioCita = Mockito.mock(RepositorioCita.class);
		ServicioCrearCita servicioCrearCita = new ServicioCrearCita(repositorioCita);
		BasePrueba.assertThrows(() -> servicioCrearCita.ejecutar(citaDesparacitacion), ExcepcionValorInvalido.class,"La mascota no esta registrada en el sistema");
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
		BasePrueba.assertNoThrows(() -> servicioCrearCita.ejecutar(citaDesparacitacion));
		BasePrueba.assertNoThrows(() -> servicioCrearCita.ejecutar(citaPeluqueria));
		BasePrueba.assertNoThrows(() -> servicioCrearCita.ejecutar(citaVacunaRabia));
		BasePrueba.assertNoThrows(() -> servicioCrearCita.ejecutar(citaVacunaTripleFelina));
	}
	@Test
	public void validarCitaFecha(){
		Cita citaDesparacitacion = new CitaTestDataBuilder().buildServicioDesparacitacion();
		Cita citaPeluqueria = new CitaTestDataBuilder().buildServicioPeluqueria();
		Cita citaVacunaRabia = new CitaTestDataBuilder().buildServicioVacunaRabia();
		Cita citaVacunaTripleFelina = new CitaTestDataBuilder().buildServicioVacunaTripleFelina();
		Cita citaSinAnteriores = new CitaTestDataBuilder().buildServicioVacunaTripleFelina();
		Cita citaFechaCorrecta = new CitaTestDataBuilder().buildServicioPeluqueria();
		Cita citaVacunaFechaCorrecta = new CitaTestDataBuilder().buildServicioPeluqueria();
		RepositorioCita repositorioCita = Mockito.mock(RepositorioCita.class);
		ServicioCrearCita servicioCrearCita = new ServicioCrearCita(repositorioCita);
		List<DtoMascota> mascotas = new ArrayList<>();
		DtoMascota dtoMascota = new CitaTestDataBuilder().buildDtoMascota();
		mascotas.add(dtoMascota);
		Mockito.when(repositorioCita.encontrarPorId(citaDesparacitacion.getIdMascota())).thenReturn(mascotas);
		Mockito.when(repositorioCita.encontrarPorId(citaPeluqueria.getIdMascota())).thenReturn(mascotas);
		Mockito.when(repositorioCita.encontrarPorId(citaVacunaRabia.getIdMascota())).thenReturn(mascotas);
		Mockito.when(repositorioCita.encontrarPorId(citaVacunaTripleFelina.getIdMascota())).thenReturn(mascotas);
		Mockito.when(repositorioCita.encontrarPorId(citaSinAnteriores.getIdMascota())).thenReturn(mascotas);
		Mockito.when(repositorioCita.encontrarPorId(citaFechaCorrecta.getIdMascota())).thenReturn(mascotas);
		Mockito.when(repositorioCita.encontrarPorId(citaVacunaFechaCorrecta.getIdMascota())).thenReturn(mascotas);
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
		List<DtoCita> citasSinAnteriores = new ArrayList<>();
		Mockito.when(repositorioCita.encontrarUltimaCitaPorMascotaYServicio(citaSinAnteriores.getIdMascota(), citaSinAnteriores.getServicio())).thenReturn(citasSinAnteriores);
		List<DtoCita> citasPeluqueriaCorrecta = new ArrayList<>();
		DtoCita ultimaCitaPeluqueriaCorrecta = new CitaTestDataBuilder().buildDtoCitaPeluqueriaCorrecta();
		citasPeluqueriaCorrecta.add(ultimaCitaPeluqueriaCorrecta);
		Mockito.when(repositorioCita.encontrarUltimaCitaPorMascotaYServicio(citaFechaCorrecta.getIdMascota(), citaFechaCorrecta.getServicio())).thenReturn(citasPeluqueriaCorrecta);
		List<DtoCita> citasVacunaCorrecta = new ArrayList<>();
		DtoCita ultimaCitaVacunaCorrecta = new CitaTestDataBuilder().buildDtoCitaVacunaCorrecta();
		citasVacunaCorrecta.add(ultimaCitaVacunaCorrecta);
		Mockito.when(repositorioCita.encontrarUltimaCitaPorMascotaYServicio(citaVacunaFechaCorrecta.getIdMascota(), citaVacunaFechaCorrecta.getServicio())).thenReturn(citasVacunaCorrecta);
		BasePrueba.assertThrows(() -> servicioCrearCita.ejecutar(citaDesparacitacion), ExcepcionValorInvalido.class,"No se cumple la fecha minima entre citas del mismo servicio");
		BasePrueba.assertThrows(() -> servicioCrearCita.ejecutar(citaPeluqueria), ExcepcionValorInvalido.class,"No se cumple la fecha minima entre citas del mismo servicio");
		BasePrueba.assertThrows(() -> servicioCrearCita.ejecutar(citaVacunaRabia), ExcepcionValorInvalido.class,"No se cumple la fecha minima entre citas del mismo servicio");
		BasePrueba.assertThrows(() -> servicioCrearCita.ejecutar(citaVacunaTripleFelina), ExcepcionValorInvalido.class,"No se cumple la fecha minima entre citas del mismo servicio");
		BasePrueba.assertNoThrows(() -> servicioCrearCita.ejecutar(citaSinAnteriores));
		BasePrueba.assertNoThrows(() -> servicioCrearCita.ejecutar(citaFechaCorrecta));
		BasePrueba.assertNoThrows(() -> servicioCrearCita.ejecutar(citaVacunaFechaCorrecta));
	}
	@Test
	public void validarCrearCita(){
		Cita cita = new CitaTestDataBuilder().buildServicioDesparacitacion();
		RepositorioCita repositorioCita = Mockito.mock(RepositorioCita.class);
		ServicioCrearCita servicioCrearCita = new ServicioCrearCita(repositorioCita);
		Mockito.when(repositorioCita.existe(cita.getIdMascota(), cita.getFecha())).thenReturn(false);
		List<DtoMascota> mascotas = new ArrayList<>();
		DtoMascota dtoMascota = new CitaTestDataBuilder().buildDtoMascotaDescuento();
		mascotas.add(dtoMascota);
		Mockito.when(repositorioCita.encontrarPorId(cita.getIdMascota())).thenReturn(mascotas);
		List<DtoCita> citasDesparacitacion = new ArrayList<>();
		DtoCita ultimaCitaDesparacitacion = new CitaTestDataBuilder().buildDtoCitaDesparacitacionCorrecta();
		citasDesparacitacion.add(ultimaCitaDesparacitacion);
		Mockito.when(repositorioCita.encontrarUltimaCitaPorMascotaYServicio(cita.getIdMascota(), cita.getServicio())).thenReturn(citasDesparacitacion);
		BasePrueba.assertNoThrows(() -> servicioCrearCita.ejecutar(cita));
	}
}
