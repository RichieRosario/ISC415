package dao;


import encapsulacion.Comentario;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

public class Sql2oComentarioDao implements ComentarioDao {

    private final Sql2o sql2o;
    public Sql2oComentarioDao(Sql2o sql2o) {this.sql2o = sql2o;}

    @Override
    public void add(Comentario comentario){


        String sql = "INSERT INTO comentarios (comentario, autor_id, articulo_id) VALUES (:comentario, " +
                ":autor_id, :articulo_id)";

        Connection con = sql2o.open();

        Long id =(Long) con.createQuery(sql, true)
                .bind(articulo)
                .executeUpdate()
                .getKey();

    }

    @Override
    public Articulo findOne(Long id) {
        Connection  con = sql2o.open();

        return con.createQuery("SELECT * FROM articulos WHERE id = :id")
                .addParameter("id", id)
                .executeAndFetchFirst(Articulo.class);
    }

    @Override
    public List<Articulo> getAll() {

        Connection con = sql2o.open();
        return con.createQuery("SELECT * FROM articulos")
                .executeAndFetch(Articulo.class);

    }

    @Override
    public void update(Articulo articulo) {

        String sql = "UPDATE articulos set titulo = :titulo, cuerpo= :cuerpo, autor_id= :autor_id, " +
                "fecja = :fecha WHERE id = :id";

        Connection con = sql2o.open();

        con.createQuery(sql)
                .bind(articulo)
                .executeUpdate();
    }

    @Override
    public void deleteById(Long id) {

        String sql = "DELETE from articulos WHERE id=:id";

        Connection con = sql2o.open();
        con.createQuery(sql)
                .addParameter("id", id)
                .executeUpdate();
    }
}
