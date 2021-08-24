package com.ceiba.mascota.adaptador.dao;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

import com.ceiba.cita.adaptador.dao.MapeoCita;
import com.ceiba.cita.modelo.dto.DtoCita;
import com.ceiba.infraestructura.jdbc.CustomNamedParameterJdbcTemplate;
import com.ceiba.infraestructura.jdbc.sqlstatement.SqlStatement;
import com.ceiba.mascota.modelo.dto.DtoMascota;
import com.ceiba.mascota.puerto.dao.DaoMascota;

@Component
public class DaoMascotaMysql implements DaoMascota {

	private final CustomNamedParameterJdbcTemplate customNamedParameterJdbcTemplate;
	
	 @SqlStatement(namespace="mascota", value="listar")
	 private static String sqlListar;
	 
	 @SqlStatement(namespace="mascota", value="encontrarPorId")
	 private static String sqlEncontrarPorId;
	 
	 @SqlStatement(namespace="cita", value="encontrarUltimaCitaPorId")
	    private static String sqlEncontrarUltimaCitaPorId;
	 
	 public DaoMascotaMysql(CustomNamedParameterJdbcTemplate customNamedParameterJdbcTemplate) {
	     this.customNamedParameterJdbcTemplate = customNamedParameterJdbcTemplate;
	 }
	 
	 public List<DtoMascota> listar(){
		 return this.customNamedParameterJdbcTemplate.getNamedParameterJdbcTemplate().query(sqlListar, new MapeoMascota());
	 }
	 
	 public DtoMascota encontrarPorId(Long id){
		 MapSqlParameterSource paramSource = new MapSqlParameterSource();
	        paramSource.addValue("id", id);
		 return this.customNamedParameterJdbcTemplate.getNamedParameterJdbcTemplate().query(sqlEncontrarPorId,paramSource,new MapeoMascota()).isEmpty()?null:this.customNamedParameterJdbcTemplate.getNamedParameterJdbcTemplate().query(sqlEncontrarPorId,paramSource,new MapeoMascota()).get(0);
	 }
	 
		public List<DtoCita> encontrarUltimaCitaPorMascotaYServicio(Long id, String servicio) {
			MapSqlParameterSource paramSource = new MapSqlParameterSource();
	        paramSource.addValue("idMascota", id);
	        paramSource.addValue("servicio", servicio);
			return this.customNamedParameterJdbcTemplate.getNamedParameterJdbcTemplate().query(sqlEncontrarUltimaCitaPorId,paramSource, new MapeoCita());
		}
	
}
