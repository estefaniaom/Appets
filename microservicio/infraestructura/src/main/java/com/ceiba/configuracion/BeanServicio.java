package com.ceiba.configuracion;

import com.ceiba.cita.puerto.repositorio.RepositorioCita;
import com.ceiba.cita.servicio.ServicioCrearCita;
import com.ceiba.mascota.puerto.repositorio.RepositorioMascota;
import com.ceiba.mascota.servicio.ServicioCrearMascota;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanServicio {
    
    @Bean
    public ServicioCrearMascota servicioCrearMascota(RepositorioMascota repositorioMascota) {
    	return new ServicioCrearMascota(repositorioMascota);
    }
	
    @Bean
    public ServicioCrearCita servicioCrearCita(RepositorioCita repositorioCita) {
    	return new ServicioCrearCita(repositorioCita);
    }

}
