package com.ceiba.mascota.adaptador.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

import com.ceiba.cita.adaptador.dao.MapeoCita;
import com.ceiba.cita.modelo.dto.DtoCita;
import com.ceiba.cita.puerto.constantes.ServiciosCita;
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
	        
	        DtoMascota dtoMascota = this.customNamedParameterJdbcTemplate.getNamedParameterJdbcTemplate().query(sqlEncontrarPorId,paramSource,new MapeoMascota()).isEmpty()?null:this.customNamedParameterJdbcTemplate.getNamedParameterJdbcTemplate().query(sqlEncontrarPorId,paramSource,new MapeoMascota()).get(0);
		 if(dtoMascota!=null){
				dtoMascota = completarProximasFechas(dtoMascota,ServiciosCita.SERVICIO_DESPARACITAR.getServicio());
				dtoMascota = completarProximasFechas(dtoMascota,ServiciosCita.SERVICIO_PELUQUERIA.getServicio());
				dtoMascota = completarProximasFechas(dtoMascota,ServiciosCita.SERVICIO_VACUNA_TRIPLEFELINA.getServicio());
				dtoMascota = completarProximasFechas(dtoMascota,ServiciosCita.SERVICIO_VACUNA_RABIA.getServicio());
			}
		 return dtoMascota;
	 }
	 
		public List<DtoCita> encontrarUltimaCitaPorMascotaYServicio(Long id, String servicio) {
			MapSqlParameterSource paramSource = new MapSqlParameterSource();
	        paramSource.addValue("idMascota", id);
	        paramSource.addValue("servicio", servicio);
			return this.customNamedParameterJdbcTemplate.getNamedParameterJdbcTemplate().query(sqlEncontrarUltimaCitaPorId,paramSource, new MapeoCita());
		}
		
		public DtoMascota completarProximasFechas(DtoMascota dtoMascota, String servicio){
			DtoCita dtoCita = encontrarUltimaCitaPorMascotaYServicio(dtoMascota.getId(), servicio).isEmpty()?null:encontrarUltimaCitaPorMascotaYServicio(dtoMascota.getId(), servicio).get(0);
			if(dtoCita!=null && dtoCita.getServicio().equals(ServiciosCita.SERVICIO_DESPARACITAR.getServicio())){
				dtoMascota.setFechaProximaDesparacitacion(calcularProximaFecha(dtoCita.getFecha(), ServiciosCita.SERVICIO_DESPARACITAR.getFrecuencia()));
			}else if(dtoCita!=null && dtoCita.getServicio().equals(ServiciosCita.SERVICIO_PELUQUERIA.getServicio())){
				dtoMascota.setFechaProximaPeluqueria(calcularProximaFecha(dtoCita.getFecha(), ServiciosCita.SERVICIO_PELUQUERIA.getFrecuencia()));
			}else if(dtoCita!=null && dtoCita.getServicio().equals(ServiciosCita.SERVICIO_VACUNA_TRIPLEFELINA.getServicio())){
				dtoMascota.setFechaProximaVacunaTripleFelina(calcularProximaFecha(dtoCita.getFecha(), ServiciosCita.SERVICIO_VACUNA_TRIPLEFELINA.getFrecuencia()));
			}else if(dtoCita!=null && dtoCita.getServicio().equals(ServiciosCita.SERVICIO_VACUNA_RABIA.getServicio())){
				dtoMascota.setFechaProximaVacunaRabia(calcularProximaFecha(dtoCita.getFecha(), ServiciosCita.SERVICIO_VACUNA_RABIA.getFrecuencia()));
			}
			return dtoMascota;
		}
		
		public LocalDateTime calcularProximaFecha(LocalDateTime fechaAnterior, int tiempoAdicional){
			LocalDateTime fechaProximoMes = fechaAnterior.plusMonths(tiempoAdicional);
			return fechaProximoMes.minusDays(3);
			
		}
	
}
