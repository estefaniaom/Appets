update mascota
set nombre = :nombre,
	fecha_nacimiento = :fechaNacimiento,
	nombre_contacto = :nombreContacto,
	telefono = :telefono,
	tipo = :tipo
where id = :id