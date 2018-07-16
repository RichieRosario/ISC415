package dao;

import modelo.Timeline;

import java.util.List;

public interface TimelineDao extends IRepositorio<Timeline, Integer>{

    @Override
    void add(Timeline timeline);

    @Override
    Timeline findOne(Integer id);

    @Override
    List<Timeline> getAll();

    @Override
    void update(Timeline timeline);

    @Override
    void deleteById(Timeline timeline);

}
