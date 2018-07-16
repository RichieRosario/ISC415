package dao;

import modelo.Comment;

import java.util.List;

public interface CommentDao extends IRepositorio<Comment, Integer> {

    @Override
    void add(Comment comment);

    @Override
    Comment findOne(Integer id);

    @Override
    List<Comment> getAll();

    @Override
    void update(Comment comment);

    @Override
    void deleteById(Comment comment);

}
