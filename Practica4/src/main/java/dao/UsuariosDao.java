package dao;

import encapsulacion.Usuario;

import java.util.List;

public interface UsuariosDao {

    void add(Usuario usuario);

    Usuario findOne(Long id);

    List<Usuario> getAll();

    void update(Usuario usuario);

    void deleteById(Long id);

    Usuario searchByUsername(String username);

    Usuario searchById(Long id);

}
