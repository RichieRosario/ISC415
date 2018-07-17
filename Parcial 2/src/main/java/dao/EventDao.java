package dao;

import modelo.Event;

import java.util.List;

public interface EventDao extends IRepositorio<Event, Integer>{

    @Override
    void add(Event event);

    @Override
    Event findOne(Integer id);

    @Override
    List<Event> getAll();

    @Override
    void update(Event event);

    @Override
    void deleteById(Event event);
}
