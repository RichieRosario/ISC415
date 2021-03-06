package dao;

import encapsulacion.Articulo;
import encapsulacion.Comentario;
import encapsulacion.Etiqueta;
import encapsulacion.Usuario;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;
import java.util.Map;

public class Sql2oArticuloDao implements ArticuloDao {

    private final Sql2o sql2o;
    public Sql2oArticuloDao(Sql2o sql2o) {this.sql2o = sql2o;}


    public Long add(Articulo articulo){


        String sql = "INSERT INTO articulos (titulo, cuerpo, autorId, fecha) VALUES ( :titulo, " +
                ":cuerpo, :autorId, :fecha)";

      //  String sql2 = "INSERT INTO articulos_etiquetas (articuloId, etiquetaId) VALUES ( :articuloId, :etiquetaId)";

        Connection con = sql2o.open();

        Long id = con.createQuery(sql, true)
                .addParameter("titulo", articulo.getTitulo())
                .addParameter("cuerpo", articulo.getCuerpo())
                .addParameter("autorId", articulo.getAutorId())
                .addParameter("fecha", articulo.getFecha())
                .executeUpdate()
                .getKey(Long.class);
//        con.createQuery(sql2)
//                .addParameter("articuloId", articulo.getId())
//                .addParameter("etiquetaId", etiqueta.getId())
//                .executeUpdate();
//        con.commit();
        return id;

    }

    public void addTablaIntermedia(Long idarticulo, Etiqueta etiqueta){

         String sql2 = "INSERT INTO articulos_etiquetas (articuloId, etiquetaId) VALUES ( :articuloId, :etiquetaId)";

        Connection con = sql2o.open();

        con.createQuery(sql2)
                .addParameter("articuloId", idarticulo)
                .addParameter("etiquetaId", etiqueta.getId())
                .executeUpdate();
    }


    public Articulo findOne(Long id) {
        Connection  con = sql2o.open();

        return con.createQuery("SELECT * FROM articulos WHERE id = :id")
                .addParameter("id", id)
                .executeAndFetchFirst(Articulo.class);
    }

    public List<Articulo> getAll() {

        Connection con = sql2o.open();
        return con.createQuery("SELECT * FROM articulos")
                .executeAndFetch(Articulo.class);

    }

    public void update(Articulo articulo) {

        String sql = "UPDATE articulos set id = :id, titulo = :titulo , cuerpo= :cuerpo, autorId= :autorId, " +
                "fecha = :fecha WHERE id = :id";

        Connection con = sql2o.open();

        con.createQuery(sql)
                .addParameter("id", articulo.getId())
                .addParameter("titulo", articulo.getTitulo())
                .addParameter("cuerpo", articulo.getCuerpo())
                .addParameter("autorId", articulo.getAutorId())
                .addParameter("fecha", articulo.getFecha())
                .executeUpdate();
    }

    public void deleteById(Long id) {

        String sql = "DELETE from articulos WHERE id=:id";
        String sql2 = "DELETE from comentarios c WHERE c.articuloId = :id" ;

        try (Connection con = sql2o.beginTransaction()) {
            con.createQuery(sql2).addParameter("id", id).executeUpdate();
            con.createQuery(sql).addParameter("id", id).executeUpdate();
            con.commit();
        }
    }

    public List<Comentario> obtenerComentarios(Long articuloid){

        String sql = "SELECT * FROM comentarios  WHERE articuloId = :articuloid";
        try(Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("articuloid", articuloid)
                    .executeAndFetch(Comentario.class);
        }
    }

    public List<Long> obtenerEtiquetas(Long articuloId){

        String sql = "SELECT etiquetaId FROM articulos_etiquetas WHERE articuloId = :articuloId";

        try(Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("articuloId", articuloId)
                     .executeAndFetch(Long.class);
        }

    }

    public Usuario searchById(Long id){

        Connection  con = sql2o.open();

        return con.createQuery("SELECT * FROM usuarios WHERE id=:id")
                .addParameter("id", id)
                .executeAndFetchFirst(Usuario.class);
    }



}
