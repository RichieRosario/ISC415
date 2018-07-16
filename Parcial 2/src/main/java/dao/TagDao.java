package dao;

import modelo.Tag;

import java.util.List;

public interface TagDao extends IRepositorio<Tag, Integer> {

    @Override
    void add(Tag tag);

    @Override
    Tag findOne(Integer id);

    @Override
    List<Tag> getAll();

    @Override
    void update(Tag tag);

    @Override
    void deleteById(Tag tag);

}
