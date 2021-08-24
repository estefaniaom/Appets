package com.ceiba.mascota.adaptador.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import org.springframework.jdbc.core.RowMapper;

import com.ceiba.infraestructura.jdbc.MapperResult;
import com.ceiba.mascota.modelo.dto.DtoMascota;

public class MapeoMascota implements RowMapper<DtoMascota>, MapperResult {

	@Override
	public DtoMascota mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Long id = rs.getLong("id");
		String nombre = rs.getString("nombre");
		LocalDateTime fechaNacimiento = extraerLocalDateTime(rs, "fecha_nacimiento");
		String nombreContacto = rs.getString("nombre_contacto");
		String telefono = rs.getString("telefono");
		String tipo = rs.getString("tipo");
		
		return new DtoMascota(id, nombre, fechaNacimiento, nombreContacto, telefono, tipo);
	}

	
	
}
