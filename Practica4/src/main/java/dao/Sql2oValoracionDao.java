package dao;

import encapsulacion.Valoracion;
import org.hibernate.Session;
import org.hibernate.Transaction;

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
import java.util.List;
import hibernate.HibernateUtil;


public class Sql2oValoracionDao extends Repositorio<Valoracion, Long>  implements ValoracionDao {

    private static final Logger logger = LoggerFactory.getLogger(Sql2oArticuloDao.class);

    public Sql2oValoracionDao(Class<Valoracion> valoracionClass) {
        super(valoracionClass);
    }

    public void add(Valoracion valoracion){

        super.add(valoracion);

    }

    public Valoracion findOne(Long id) {

        return super.findOne(id);
    }

    public List<Valoracion> getAll() {


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

    public void update(Valoracion valoracion) {

        super.update(valoracion);
    }

    public void deleteById(Valoracion valoracion) {
        valoracion.setDeleted(true);
        this.update(valoracion);
    }





}
