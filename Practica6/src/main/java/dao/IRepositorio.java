package dao;

import java.util.List;

public interface IRepositorio<T,K extends Long> {

    void add(T k);

    void update(T k);

    void deleteById(T k);

    void merge(T k);

    T findOne(K k);

    List<T> getAll();
}


