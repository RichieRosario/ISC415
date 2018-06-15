package dao;

import encapsulacion.Etiqueta;

import java.util.List;

public interface EtiquetaDao{
    void add(Etiqueta etiqueta);

    Etiqueta findOne(Long id);

    List<Etiqueta> getAll();

    void update(Etiqueta etiqueta);

    void deleteById(Long id);
}