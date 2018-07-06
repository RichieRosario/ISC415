package dao;

import encapsulacion.Usuario;

import java.util.List;

public interface UsuariosDao extends IRepositorio<Usuario, Long> {

    @Override
    void add(Usuario usuario);

    @Override
    Usuario findOne(Long id);

    @Override
    List<Usuario> getAll();

    @Override
    void update(Usuario usuario);

    @Override
    void deleteById(Usuario usuario);

    Usuario searchByUsername(String username);
//
    Usuario searchById(Long id);

}
