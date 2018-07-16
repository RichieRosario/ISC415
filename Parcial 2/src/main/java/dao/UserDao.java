package dao;

import modelo.User;

import java.util.List;

public interface UserDao extends IRepositorio<User, Integer> {

    @Override
    void add(User user);

    @Override
    User findOne(Integer id);

    @Override
    List<User> getAll();

    @Override
    void update(User user);

    @Override
    void deleteById(User user);

}
