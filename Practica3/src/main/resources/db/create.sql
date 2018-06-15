CREATE TABLE IF NOT EXISTS usuarios (

  id            BIGINT PRIMARY KEY,
  username      VARCHAR2(255),
  nombre        VARCHAR2(255),
  password      VARCHAR2(255),
  administrator BOOLEAN,
  autor         BOOLEAN
);

CREATE TABLE IF NOT EXISTS etiquetas (
  id       BIGINT PRIMARY KEY,
  etiqueta VARCHAR2(255)
);

CREATE TABLE IF NOT EXISTS articulos (
  id       BIGINT PRIMARY KEY,
  titulo VARCHAR2(255),
  cuerpo   VARCHAR2(255),
  autor_id BIGINT REFERENCES usuarios(id) ON UPDATE CASCADE,
  fecha    DATE
);

CREATE TABLE IF NOT EXISTS comentarios (
  id          BIGINT PRIMARY KEY,
  comentario VARCHAR2(255),
  autor_id    BIGINT REFERENCES usurios(id) ON UPDATE CASCADE,
  articulo_id BIGINT REFERENCES articulos(id) ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS articulos_comentarios (
  articulo_id  BIGINT REFERENCES articulos(id) ON UPDATE CASCADE,
  comentario_id BIGINT REFERENCES comentarios (id) ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS articulos_etiquetas (
  articulo_id BIGINT REFERENCES articulos(id) ON UPDATE CASCADE,
  etiqueta_id BIGINT REFERENCES etiquetas(id) ON UPDATE CASCADE
);