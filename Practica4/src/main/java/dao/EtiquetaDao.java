package dao;

import encapsulacion.Etiqueta;

import java.util.List;

public interface EtiquetaDao{
    void add(Etiqueta etiqueta);

    Etiqueta findOne(Long id);

    Etiqueta searchByTag(String tag);

    List<Etiqueta> getAll();

    void update(Etiqueta etiqueta);

    void deleteById(Long id);
}