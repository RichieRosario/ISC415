package dao;

import javafx.geometry.Pos;
import modelo.Post;

import java.util.List;

public interface PostDao extends IRepositorio<Post, Integer>{

    @Override
    void add(Post post);

    @Override
    Post findOne(Integer id);

    @Override
    List<Post> getAll();

    @Override
    void update(Post post);

    @Override
    void deleteById(Post post);

}
