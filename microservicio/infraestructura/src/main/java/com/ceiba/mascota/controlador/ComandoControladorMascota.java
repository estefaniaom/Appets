package com.ceiba.mascota.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ceiba.ComandoRespuesta;
import com.ceiba.mascota.comando.ComandoMascota;
import com.ceiba.mascota.comando.manejador.ManejadorCrearMascota;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/mascotas")
@Api(tags = { "Controlador comando mascota"})
public class ComandoControladorMascota {

	private final ManejadorCrearMascota manejadorCrearMascota;
	
	@Autowired
	public ComandoControladorMascota(ManejadorCrearMascota manejadorCrearMascota){
		this.manejadorCrearMascota = manejadorCrearMascota;
	}
	
	@PostMapping
    @ApiOperation("Crear Mascota")
    public ComandoRespuesta<Long> crear(@RequestBody ComandoMascota comandoMascota) {
        return manejadorCrearMascota.ejecutar(comandoMascota);
    }
}
