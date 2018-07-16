package dao;

import modelo.Album;

import java.util.List;

public interface AlbumDao extends IRepositorio<Album, Integer> {

    @Override
    void add(Album album);

    @Override
    Album findOne(Integer id);

    @Override
    List<Album> getAll();

    @Override
    void update(Album album);

    @Override
    void deleteById(Album album);

}
