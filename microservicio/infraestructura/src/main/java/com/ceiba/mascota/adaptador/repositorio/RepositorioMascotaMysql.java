package com.ceiba.mascota.adaptador.repositorio;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.ceiba.infraestructura.jdbc.CustomNamedParameterJdbcTemplate;
import com.ceiba.infraestructura.jdbc.sqlstatement.SqlStatement;
import com.ceiba.mascota.modelo.entidad.Mascota;
import com.ceiba.mascota.puerto.repositorio.RepositorioMascota;

@Repository
public class RepositorioMascotaMysql implements RepositorioMascota{

	private final CustomNamedParameterJdbcTemplate customNamedParameterJdbcTemplate;

    @SqlStatement(namespace="mascota", value="crear")
    private static String sqlCrear;

    @SqlStatement(namespace="mascota", value="actualizar")
    private static String sqlActualizar;

    @SqlStatement(namespace="mascota", value="eliminar")
    private static String sqlEliminar;

    @SqlStatement(namespace="mascota", value="existe")
    private static String sqlExiste;
	public RepositorioMascotaMysql(CustomNamedParameterJdbcTemplate customNamedParameterJdbcTemplate){
		this.customNamedParameterJdbcTemplate = customNamedParameterJdbcTemplate;
	}
	@Override
	public Long crear(Mascota mascota) {
		return this.customNamedParameterJdbcTemplate.crear(mascota, sqlCrear);
	}
	@Override
	public boolean existe(String nombre, String nombreContacto) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("nombre", nombre);
        paramSource.addValue("nombreContacto", nombreContacto);
        return this.customNamedParameterJdbcTemplate.getNamedParameterJdbcTemplate().queryForObject(sqlExiste,paramSource, Boolean.class);
	}
}
