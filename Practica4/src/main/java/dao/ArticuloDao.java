package dao;

import encapsulacion.Articulo;
import encapsulacion.Comentario;
import encapsulacion.Etiqueta;
import encapsulacion.Usuario;

import java.util.List;

public interface ArticuloDao {

    Long add(Articulo articulo);

    void addTablaIntermedia(Long idarticulo, Etiqueta etiqueta);

    Articulo findOne(Long id);

    List<Articulo> getAll();

    void update(Articulo articulo);

    void deleteById(Long id);

    List<Comentario> obtenerComentarios(Long id);

    List<Long> obtenerEtiquetas(Long id);

    Usuario searchById(Long id);

}