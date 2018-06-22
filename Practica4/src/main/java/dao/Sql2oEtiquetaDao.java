package dao;

import encapsulacion.Etiqueta;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.UUID;

import static java.util.jar.Pack200.Packer.PASS;

public class Sql2oEtiquetaDao implements EtiquetaDao {

    private final Sql2o sql2o;
    public Sql2oEtiquetaDao(Sql2o sql2o) {this.sql2o = sql2o;}


    @Override
    public void add(Etiqueta etiquetas){



        String sql = "INSERT INTO etiquetas (id, etiqueta) VALUES (:id, :etiqueta);";

        Connection con = sql2o.open();

              con.createQuery(sql, true)
                .addParameter("id", etiquetas.getId())
                      .addParameter("etiqueta", etiquetas.getEtiqueta())
                .executeUpdate();

    }

    @Override
    public Etiqueta findOne(Long id) {
        Connection  con = sql2o.open();

        return con.createQuery("SELECT * FROM etiquetas WHERE id = :id")
                .addParameter("id", id)
                .executeAndFetchFirst(Etiqueta.class);
    }

    @Override
    public Etiqueta searchByTag(String tag) {
        Connection  con = sql2o.open();

        return con.createQuery("SELECT * FROM etiquetas WHERE etiqueta = :etiqueta")
                .addParameter("etiqueta", tag)
                .executeAndFetchFirst(Etiqueta.class);
    }

    @Override
    public List<Etiqueta> getAll() {

        Connection con = sql2o.open();
        return con.createQuery("SELECT * FROM etiquetas")
                .executeAndFetch(Etiqueta.class);

    }

    @Override
    public void update(Etiqueta etiqueta) {

        String sql = "UPDATE etiquetas set etiqueta = :etiqueta WHERE id = :id";

        Connection con = sql2o.open();

        con.createQuery(sql)
                .bind(etiqueta)
                .executeUpdate();
    }

    @Override
    public void deleteById(Long id) {

        String sql = "DELETE from etiquetas WHERE id=:id";

        Connection con = sql2o.open();
        con.createQuery(sql)
                .addParameter("id", id)
                .executeUpdate();
    }
}
