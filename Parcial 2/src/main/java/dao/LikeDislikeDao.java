package dao;

import modelo.LikeDislike;

import java.util.List;

public interface LikeDislikeDao extends IRepositorio<LikeDislike, Integer> {

    @Override
    void add(LikeDislike likeDislike);

    @Override
    LikeDislike findOne(Integer id);

    @Override
    List<LikeDislike> getAll();

    @Override
    void update(LikeDislike likeDislike);

    @Override
    void deleteById(LikeDislike likeDislike);

}
