package dao;

import modelo.Wall;
import java.util.List;

public interface WallDao extends IRepositorio<Wall, Integer> {

    @Override
    void add(Wall wall);

    @Override
    Wall findOne(Integer id);

    @Override
    List<Wall> getAll();

    @Override
    void update(Wall wall);

    @Override
    void deleteById(Wall wall);
}
