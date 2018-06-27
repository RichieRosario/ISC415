package dao;

import encapsulacion.Etiqueta;

import java.util.List;

public interface EtiquetaDao extends IRepositorio<Etiqueta, Long>{

    @Override
    void add(Etiqueta etiqueta);

    @Override
    Etiqueta findOne(Long id);

//    @Override
    Etiqueta searchByTag(String tag);

    @Override
    List<Etiqueta> getAll();

    @Override
    void update(Etiqueta etiqueta);

    @Override
    void deleteById(Etiqueta etiqueta);
}