package dao;

import encapsulacion.Articulo;
import encapsulacion.Comentario;

import java.util.List;

public interface ArticuloDao {

    void add(Articulo articulo);

    Articulo findOne(Long id);

    List<Articulo> getAll();

    void update(Articulo articulo);

    void deleteById(Long id);

    List<Comentario> obtenerComentarios(Long id);
}