package com.ceiba.cita.adaptador.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import org.springframework.jdbc.core.RowMapper;

import com.ceiba.cita.modelo.dto.DtoCita;
import com.ceiba.infraestructura.jdbc.MapperResult;

public class MapeoCita implements RowMapper<DtoCita>, MapperResult{

	@Override
	public DtoCita mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Long id = rs.getLong("id");
		LocalDateTime fecha = extraerLocalDateTime(rs, "fecha");
		String servicio = rs.getString("servicio");
		Long idMascota = rs.getLong("idMascota");
		String nombre = rs.getString("nombre");
		Long precio = rs.getLong("precio");
		
		return new DtoCita(id, fecha, servicio, idMascota, nombre, precio);
	}
}
