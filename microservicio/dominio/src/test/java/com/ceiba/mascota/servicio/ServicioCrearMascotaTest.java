package com.ceiba.mascota.servicio;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.mockito.Mockito;

import com.ceiba.BasePrueba;
import com.ceiba.dominio.excepcion.ExcepcionDuplicidad;
import com.ceiba.mascota.modelo.entidad.Mascota;
import com.ceiba.mascota.puerto.repositorio.RepositorioMascota;
import com.ceiba.mascota.servicio.testdatabuilder.MascotaTestDataBuilder;


public class ServicioCrearMascotaTest {
	
	@Test
	public void validarCrearMascota(){
		Mascota mascota = new MascotaTestDataBuilder().build();
		RepositorioMascota repositorioMascota = Mockito.mock(RepositorioMascota.class);
		ServicioCrearMascota servicioCrearMascota = new ServicioCrearMascota(repositorioMascota);
		Long idMascota = servicioCrearMascota.ejecutar(mascota);
		assertNotNull(idMascota);
	}
	
	
	@Test
    public void validarMascotaExistenciaPreviaTest() {
		Mascota mascota = new MascotaTestDataBuilder().build();
		RepositorioMascota repositorioMascota = Mockito.mock(RepositorioMascota.class);
        Mockito.when(repositorioMascota.existe(mascota.getNombre(), mascota.getNombreContacto())).thenReturn(true);
        ServicioCrearMascota servicioCrearMascota = new ServicioCrearMascota(repositorioMascota);
        BasePrueba.assertThrows(() -> servicioCrearMascota.ejecutar(mascota), ExcepcionDuplicidad.class,"La mascota ya existe en el sistema");
    }
	
}
