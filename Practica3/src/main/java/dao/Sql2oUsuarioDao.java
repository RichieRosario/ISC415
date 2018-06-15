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


        String sql = "INSERT INTO usuarios (username, nombre, password, administrator, autor) VALUES (:username, :nombre, " +
                ":password, :administrator, :autor)";

        Connection con = sql2o.open();

        Long id = con.createQuery(sql, true)

                .addParameter("username", usuario.getUsername())
                .addParameter("nombre", usuario.getNombre())
                .addParameter("password", usuario.getPassword())
                .addParameter("administrator", usuario.isAdministrator())
                .addParameter("autor", usuario.isAutor())
                .executeUpdate()
                .getKey(Long.class);

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
                .addParameter("id", usuario.getId())
                .addParameter("username", usuario.getUsername())
                .addParameter("nombre", usuario.getNombre())
                .addParameter("password", usuario.getPassword())
                .addParameter("administrator", usuario.isAdministrator())
                .addParameter("autor", usuario.isAutor())
                .executeUpdate();
    }

    @Override
    public void deleteById(Long id) {

        String sql = "DELETE from comentarios c WHERE c.autorId=:id";
        String sql2 = "DELETE from articulos a WHERE a.autorID = :id" ;
        String sql3 = "DELETE from usuarios u WHERE u.id = :id";

        try (Connection con = sql2o.beginTransaction()) {
            con.createQuery(sql).addParameter("id", id).executeUpdate();
            con.createQuery(sql2).addParameter("id", id).executeUpdate();
            con.createQuery(sql3).addParameter("id", id).executeUpdate();
            con.commit();
        }
    }

    @Override
    public Usuario searchByUsername(String username){

        Connection  con = sql2o.open();

        return con.createQuery("SELECT * FROM usuarios WHERE username = :username")
                .addParameter("username", username)
                .executeAndFetchFirst(Usuario.class);
    }

    @Override
    public Usuario searchById(Long id){

        Connection  con = sql2o.open();

        return con.createQuery("SELECT * FROM usuarios WHERE id=:id")
                .addParameter("id", id)
                .executeAndFetchFirst(Usuario.class);
    }


}
