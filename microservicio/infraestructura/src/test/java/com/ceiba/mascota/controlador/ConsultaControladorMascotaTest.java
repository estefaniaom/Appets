package com.ceiba.mascota.controlador;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.ceiba.ApplicationMock;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes= ApplicationMock.class)
@WebMvcTest(ConsultaControladorMascota.class)
public class ConsultaControladorMascotaTest {

	 @Autowired
	    private MockMvc mocMvc;

	    @Test
	    public void listar() throws Exception {
	        mocMvc.perform(get("/mascotas")
	                .contentType(MediaType.APPLICATION_JSON))
	                .andExpect(status().isOk())
	                .andExpect(jsonPath("$[0].nombre", is("Sky")));
	    }
	    
	    @Test
	    public void encontrarPorId() throws Exception {
	        mocMvc.perform(get("/mascotas/1")
	                .contentType(MediaType.APPLICATION_JSON))
	                .andExpect(status().isOk())
	                .andExpect(jsonPath("nombre", is("Sky")));
	    }
	
}
