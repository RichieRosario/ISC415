package dao;

import modelo.Profile;

import java.util.List;

public interface ProfileDao extends IRepositorio<Profile, Integer> {

    @Override
    void add(Profile profile);

    @Override
    Profile findOne(Integer id);

    @Override
    List<Profile> getAll();

    @Override
    void update(Profile profile);

    @Override
    void deleteById(Profile profile);

}
