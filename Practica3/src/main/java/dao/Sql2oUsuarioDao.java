package dao;

import encapsulacion.Usuario;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

public class Sql2oUsuarioDao implements UsuariosDao{
    private final Sql2o sql2o;
    public Sql2oUsuarioDao(Sql2o sql2o) {this.sql2o = sql2o;}

    @Override
    public void add(Usuario usuario){


        String sql = "INSERT INTO usuarios (id, username, nombre, password, administrator, autor) VALUES (:id, :username, :nombre, " +
                ":password, :administrator, :autor)";

        Connection con = sql2o.open();

                 con.createQuery(sql)
                .bind(usuario)
                .executeUpdate();

    }

    @Override
    public Usuario findOne(Long id) {
        Connection  con = sql2o.open();

        return con.createQuery("SELECT * FROM usuarios WHERE id = :id")
                .addParameter("id", id)
                .executeAndFetchFirst(Usuario.class);
    }

    @Override
    public List<Usuario> getAll() {

        Connection con = sql2o.open();
        return con.createQuery("SELECT * FROM usuarios")
                .executeAndFetch(Usuario.class);

    }

    @Override
    public void update(Usuario usuario) {

        String sql = "UPDATE usuarios set id = :id, username = :username, nombre = :nombre, password = :password, " +
                "administrator = :administrator, autor = :autor WHERE id = :id";

        Connection con = sql2o.open();

        con.createQuery(sql)
                .bind(usuario)
                .executeUpdate();
    }

    @Override
    public void deleteById(Long id) {

        String sql = "DELETE from usuarios WHERE id=:id";

        Connection con = sql2o.open();
        con.createQuery(sql)
                .addParameter("id", id)
                .executeUpdate();
    }


}