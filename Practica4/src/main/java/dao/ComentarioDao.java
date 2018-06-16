package dao;

import encapsulacion.Articulo;
import encapsulacion.Comentario;
import encapsulacion.Usuario;

import java.util.List;

public interface ComentarioDao{
    void add(Comentario comentario);

    Comentario findOne(Long id);

    List<Comentario> getAll();

    void update(Comentario comentario);

    void deleteById(Long id);
}