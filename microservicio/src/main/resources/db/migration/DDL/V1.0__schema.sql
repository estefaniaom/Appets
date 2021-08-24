create table mascota (
 id int(11) not null auto_increment,
 nombre varchar(100) not null,
 fecha_nacimiento datetime null,
 nombre_contacto varchar(100) not null,
 telefono varchar(20) null,
 tipo varchar(100) null,
 primary key (id)
);

create table cita (
 id int(11) not null auto_increment,
 fecha datetime not null,
 servicio varchar(100) not null,
 idmascota int(11) not null,
 nombre varchar(100) not null,
 precio int(11) null,
 primary key (id)
);