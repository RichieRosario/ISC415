
package dao;

import encapsulacion.Articulo;
import encapsulacion.Comentario;
import encapsulacion.Etiqueta;
import encapsulacion.Usuario;
import hibernate.HibernateUtil;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Sql2oArticuloDao extends Repositorio<Articulo, Long>  implements ArticuloDao {

    private static final Logger logger = LoggerFactory.getLogger(Sql2oArticuloDao.class);

    public Sql2oArticuloDao(Class<Articulo> articuloClass) {
        super(articuloClass);
    }


    public void add(Articulo articulo){

        super.add(articulo);

    }
//
//    public void addTablaIntermedia(Long idarticulo, Etiqueta etiqueta){
//
//         String sql2 = "INSERT INTO articulos_etiquetas (articuloId, etiquetaId) VALUES ( :articuloId, :etiquetaId)";
//
//        Connection con = sql2o.open();
//
//        con.createQuery(sql2)
//                .addParameter("articuloId", idarticulo)
//                .addParameter("etiquetaId", etiqueta.getId())
//                .executeUpdate();
//    }


    public Articulo findOne(Long id) {

        return super.findOne(id);
    }

    public List<Articulo> getAll() {
//
//        Connection con = sql2o.open();
//        return con.createQuery("SELECT * FROM articulos")
//                .executeAndFetch(Articulo.class);

        Session session = null;
        Transaction transaction = null;
        Query query = null;

        try {
            session = HibernateUtil.openSession();


            transaction = session.beginTransaction();

            query = session.createQuery("from Articulo a order by a.fecha desc ");

            return query.list();
        } catch (HibernateException e) {
            transaction.rollback();
            logger.debug("Error al ejecutar un select el objeto en la base de datos.", e);
            return null;
        } finally {
            session.close();
        }

    }

    public void update(Articulo articulo) {

        super.update(articulo);
    }

    public void deleteById(Articulo articulo) {
        articulo.setDeleted(true);
        this.update(articulo);
    }

    public List<Comentario> obtenerComentarios(Long articuloid){

        Session session = null;
        Transaction transaction = null;
        Query query = null;
        List<Comentario> list = new ArrayList<>();
        Sql2oComentarioDao comentarioDao = null;

        try {
            session = HibernateUtil.openSession();
            transaction = session.beginTransaction();
            query = session.createNativeQuery("SELECT etiquetaId FROM ARTICULOETIQUETAS WHERE articuloId = " + articuloid);

            for (Object object : query.list()) {

                    list.add(comentarioDao.findOne(Long.parseLong(object.toString())));
            }

            return list;

        } catch (HibernateException e) {
            transaction.rollback();
            logger.debug("Error al ejecutar un select el objeto en la base de datos.", e);
            return null;
        } finally {
            session.close();
        }

    }

    public List<Long> obtenerEtiquetas(Long articuloId) {
//
        Session session = null;
        Transaction transaction = null;
        Query query = null;
        List<Long> list = new ArrayList<>();
        Sql2oEtiquetaDao etiquetaDao = null;

        try {
            session = HibernateUtil.openSession();
            transaction = session.beginTransaction();

            query = session.createNativeQuery("SELECT etiquetaId FROM ARTICULOETIQUETAS WHERE articuloId = " + articuloId);

            for (Object object : query.list()) {
                list.add(Long.parseLong(object.toString()));
            }

            return list;

        } catch (HibernateException e) {
            transaction.rollback();
            logger.debug("Error al ejecutar un select el objeto en la base de datos.", e);
            return null;
        } finally {
            session.close();
        }
    }
//        String sql = "SELECT etiquetaId FROM articulos_etiquetas WHERE articuloId = :articuloId";
//
//        try(Connection con = sql2o.open()) {
//            return con.createQuery(sql)
//                    .addParameter("articuloId", articuloId)
//                     .executeAndFetch(Long.class);
//        }
//
//    }
//
//    public List<String> obtenerEtiquetas2(Long articuloId){
//
//        String sql = "SELECT etiquetaId FROM articulos_etiquetas WHERE articuloId = :articuloId";
//
//        try(Connection con = sql2o.open()) {
//            return con.createQuery(sql)
//                    .addParameter("articuloId", articuloId)
//                    .executeAndFetch(String.class);
//        }
//
//    }
//
    public Usuario searchById(Long id) {

        Session session = null;
        Query query = null;

        session = HibernateUtil.buildSessionFactory().openSession();
        return session.find(Usuario.class, id);
    }

}
