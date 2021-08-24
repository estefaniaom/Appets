package com.ceiba.cita.adaptador.repositorio;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.ceiba.cita.adaptador.dao.MapeoCita;
import com.ceiba.cita.modelo.dto.DtoCita;
import com.ceiba.cita.modelo.entidad.Cita;
import com.ceiba.cita.puerto.repositorio.RepositorioCita;
import com.ceiba.infraestructura.jdbc.CustomNamedParameterJdbcTemplate;
import com.ceiba.infraestructura.jdbc.sqlstatement.SqlStatement;
import com.ceiba.mascota.adaptador.dao.MapeoMascota;
import com.ceiba.mascota.modelo.dto.DtoMascota;

@Repository
public class RepositorioCitaMysql implements RepositorioCita{

	private final CustomNamedParameterJdbcTemplate customNamedParameterJdbcTemplate;

    @SqlStatement(namespace="cita", value="crear")
    private static String sqlCrear;

    @SqlStatement(namespace="cita", value="actualizar")
    private static String sqlActualizar;

    @SqlStatement(namespace="cita", value="eliminar")
    private static String sqlEliminar;

    @SqlStatement(namespace="cita", value="existe")
    private static String sqlExiste;
    
    @SqlStatement(namespace="cita", value="encontrarUltimaCitaPorId")
    private static String sqlEncontrarUltimaCitaPorId;
    
    @SqlStatement(namespace="mascota", value="encontrarPorId")
    private static String sqlEncontrarPorId;
    
    public RepositorioCitaMysql(CustomNamedParameterJdbcTemplate customNamedParameterJdbcTemplate){
		this.customNamedParameterJdbcTemplate = customNamedParameterJdbcTemplate;
	}

	@Override
	public Long crear(Cita cita) {
		return this.customNamedParameterJdbcTemplate.crear(cita, sqlCrear);
	}

	@Override
	public boolean existe(Long idMascota, LocalDateTime fecha) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("idMascota", idMascota);
        paramSource.addValue("fecha", fecha);
        return this.customNamedParameterJdbcTemplate.getNamedParameterJdbcTemplate().queryForObject(sqlExiste,paramSource, Boolean.class);
	}

	@Override
	public List<DtoMascota> encontrarPorId(Long id) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("id", id);
		return this.customNamedParameterJdbcTemplate.getNamedParameterJdbcTemplate().query(sqlEncontrarPorId,paramSource, new MapeoMascota());
	}
	
	@Override
	public List<DtoCita> encontrarUltimaCitaPorMascotaYServicio(Long id, String servicio) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("idMascota", id);
        paramSource.addValue("servicio", servicio);
		return this.customNamedParameterJdbcTemplate.getNamedParameterJdbcTemplate().query(sqlEncontrarUltimaCitaPorId,paramSource, new MapeoCita());
	}
	
}
