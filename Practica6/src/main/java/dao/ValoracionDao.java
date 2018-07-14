package dao;

import encapsulacion.Articulo;
import encapsulacion.Comentario;
import encapsulacion.Usuario;
import encapsulacion.Valoracion;

import java.util.List;


public interface ValoracionDao extends IRepositorio<Valoracion, Long>{

    @Override
    void add(Valoracion valoracion);


    @Override
    Valoracion findOne(Long id);

    @Override
    List<Valoracion> getAll();

    @Override
    void update(Valoracion valoracion);

    @Override
    void deleteById(Valoracion valoracion);

}
