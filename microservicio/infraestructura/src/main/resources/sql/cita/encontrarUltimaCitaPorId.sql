select id,fecha,servicio,idmascota,nombre,precio
from cita
where idmascota = :idMascota and servicio = :servicio
order by fecha desc