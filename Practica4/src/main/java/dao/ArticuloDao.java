package dao;

import encapsulacion.Articulo;
import encapsulacion.Comentario;
import encapsulacion.Etiqueta;
import encapsulacion.Usuario;

import java.util.List;

public interface ArticuloDao extends IRepositorio<Articulo, Long> {

    @Override
    void add(Articulo articulo);

//    void addTablaIntermedia(Long idarticulo, Etiqueta etiqueta);

    @Override
    Articulo findOne(Long id);

    @Override
    List<Articulo> getAll();

    @Override
    void update(Articulo articulo);

//    @Override
    void deleteById(Articulo articulo);

    List<Comentario> obtenerComentarios(Long id);
//
    List<Long> obtenerEtiquetas(Long id);
//
//    List<String> obtenerEtiquetas2(Long id);
//
    Usuario searchById(Long id);

}