CREATE DATABASE IF NOT EXISTS coworking CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE coworking;

CREATE TABLE IF NOT EXISTS usuario (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  nombre VARCHAR(100) NOT NULL,
  email VARCHAR(100) NOT NULL UNIQUE,
  password VARCHAR(255),
  rol ENUM('ADMIN','USUARIO')
);

CREATE TABLE IF NOT EXISTS espacios (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  nombre VARCHAR(100) NOT NULL,
  descripcion TEXT,
  capacidad INT,
  ubicacion VARCHAR(100),
  imagen_url VARCHAR(255),
  disponible BOOLEAN DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS reservas (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  usuario_id BIGINT NOT NULL,
  espacio_id BIGINT NOT NULL,
  fecha_reserva DATE NOT NULL,
  hora_inicio TIME NOT NULL,
  hora_fin TIME NOT NULL,
  estado ENUM('PENDIENTE','CONFIRMADA','CANCELADA') DEFAULT 'PENDIENTE',
  CONSTRAINT fk_reserva_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE CASCADE,
  CONSTRAINT fk_reserva_espacio FOREIGN KEY (espacio_id) REFERENCES espacios(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS token_restablecer_contrasena (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  usuario_id BIGINT NOT NULL,
  codigo_verificacion VARCHAR(255) NOT NULL,
  fecha_expiracion DATETIME NOT NULL,
  usado BOOLEAN DEFAULT FALSE,
  fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_token_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE CASCADE
);