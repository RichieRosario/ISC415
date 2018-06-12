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


        String sql = "INSERT INTO comentarios (comentario, autorId, articuloId) VALUES (:comentario, " +
                ":autorId, :articuloId)";

        Connection con = sql2o.open();

        Long id =(Long) con.createQuery(sql, true)
                .bind(comentario)
                .executeUpdate()
                .getKey();

    }

    @Override
    public Comentario findOne(Long id) {
        Connection  con = sql2o.open();

        return con.createQuery("SELECT * FROM comentarios WHERE id = :id")
                .addParameter("id", id)
                .executeAndFetchFirst(Comentario.class);
    }

    @Override
    public List<Comentario> getAll() {

        Connection con = sql2o.open();
        return con.createQuery("SELECT * FROM comentarios")
                .executeAndFetch(Comentario.class);

    }

    @Override
    public void update(Comentario comentario) {

        String sql = "UPDATE comentarios set comentario = :comentario, autor_di= :autorId, articuloId= :articuloId, " +
                " WHERE id = :id";

        Connection con = sql2o.open();

        con.createQuery(sql)
                .bind(comentario)
                .executeUpdate();
    }

    @Override
    public void deleteById(Long id) {

        String sql = "DELETE from comentarios WHERE id=:id";

        Connection con = sql2o.open();
        con.createQuery(sql)
                .addParameter("id", id)
                .executeUpdate();
    }
}
