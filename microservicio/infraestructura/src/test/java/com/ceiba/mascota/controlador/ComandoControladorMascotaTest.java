package com.ceiba.mascota.controlador;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import com.ceiba.ApplicationMock;
import com.ceiba.mascota.comando.ComandoMascota;
import com.ceiba.mascota.servicio.testdatabuilder.ComandoMascotaTestDataBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes= ApplicationMock.class)
@WebMvcTest(ComandoControladorMascota.class)
public class ComandoControladorMascotaTest {

	@Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mocMvc;
    
    @Test
    public void crear() throws Exception{
    	ComandoMascota comandoMascota = new ComandoMascotaTestDataBuilder().build();
    	
    	 mocMvc.perform(post("/mascotas")
                 .contentType(MediaType.APPLICATION_JSON)
                 .content(objectMapper.writeValueAsString(comandoMascota)))
                 .andExpect(status().isOk())
                 .andExpect(content().json("{'valor': 2}"));
    }
	
}
