package dao;

import encapsulacion.Articulo;
import encapsulacion.Comentario;
import encapsulacion.Usuario;

import java.util.List;

public interface ComentarioDao extends IRepositorio<Comentario, Long> {


    void add(Comentario comentario);

    @Override
    Comentario findOne(Long id);

    @Override
    List<Comentario> getAll();

    @Override
    void update(Comentario comentario);

    //    @Override
    void deleteById(Comentario comentario);
}