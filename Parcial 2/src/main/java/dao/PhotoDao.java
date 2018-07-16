package dao;

import modelo.Photo;

import java.util.List;

public interface PhotoDao extends IRepositorio<Photo, Integer> {

    @Override
    void add(Photo photo);

    @Override
    Photo findOne(Integer id);

    @Override
    List<Photo> getAll();

    @Override
    void update(Photo photo);

    @Override
    void deleteById(Photo photo);

}
