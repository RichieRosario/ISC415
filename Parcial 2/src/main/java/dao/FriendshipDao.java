package dao;

import modelo.Friendship;

import java.util.List;

public interface FriendshipDao extends IRepositorio<Friendship, Integer> {

    @Override
    void add(Friendship friendship);

    @Override
    Friendship findOne(Integer id);

    @Override
    List<Friendship> getAll();

    @Override
    void update(Friendship friendship);

    @Override
    void deleteById(Friendship friendship);

}
