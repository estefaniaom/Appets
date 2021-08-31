package com.ceiba.mascota.adaptador.dao;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
				dtoMascota.setFechaProximaDesparacitacion(completarProximasFechas(dtoMascota,ServiciosCita.SERVICIO_DESPARACITAR.getServicio()));
				dtoMascota.setFechaProximaPeluqueria(completarProximasFechas(dtoMascota,ServiciosCita.SERVICIO_PELUQUERIA.getServicio()));
				dtoMascota.setFechaProximaVacunaTripleFelina(completarProximasFechas(dtoMascota,ServiciosCita.SERVICIO_VACUNA_TRIPLEFELINA.getServicio()));
				dtoMascota.setFechaProximaVacunaRabia(completarProximasFechas(dtoMascota,ServiciosCita.SERVICIO_VACUNA_RABIA.getServicio()));
			}
		 return dtoMascota;
	 }
	 
		public List<DtoCita> encontrarUltimaCitaPorMascotaYServicio(Long id, String servicio) {
			MapSqlParameterSource paramSource = new MapSqlParameterSource();
	        paramSource.addValue("idMascota", id);
	        paramSource.addValue("servicio", servicio);
			return this.customNamedParameterJdbcTemplate.getNamedParameterJdbcTemplate().query(sqlEncontrarUltimaCitaPorId,paramSource, new MapeoCita());
		}
		
		private LocalDateTime completarProximasFechas(DtoMascota dtoMascota, String servicio){
			DtoCita dtoCita = encontrarUltimaCitaPorMascotaYServicio(dtoMascota.getId(), servicio).isEmpty()?null:encontrarUltimaCitaPorMascotaYServicio(dtoMascota.getId(), servicio).get(0);
			
			ServiciosCita[] listaServicios = ServiciosCita.values();
			Optional<ServiciosCita> servicioBuscado = Arrays.stream(listaServicios).filter(servicioCita -> servicioCita.getServicio().equals(servicio)).findFirst();
			if(dtoCita!=null && servicioBuscado.isPresent()){
				return calcularProximaFecha(dtoCita.getFecha(), servicioBuscado.get().getFrecuencia());
			}
			return null;
		}
		
		private LocalDateTime calcularProximaFecha(LocalDateTime fechaAnterior, int tiempoAdicional){
			LocalDateTime fechaProximoMes = fechaAnterior.plusMonths(tiempoAdicional);
			return fechaProximoMes.minusDays(3);
			
		}
	
}
