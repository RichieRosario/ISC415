package dao;

import encapsulacion.Etiqueta;
import hibernate.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.HibernateException;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.UUID;

import static java.util.jar.Pack200.Packer.PASS;

public class Sql2oEtiquetaDao extends Repositorio<Etiqueta, Long> implements EtiquetaDao {


    public Sql2oEtiquetaDao(Class<Etiqueta> etiquetaClass) {
        super(etiquetaClass);
    }

    @Override
    public void add(Etiqueta etiqueta) {
        super.add(etiqueta);
    }

    @Override
    public Etiqueta findOne(Long aLong) {
        return super.findOne(aLong);
    }

    @Override
    public Etiqueta searchByTag(String etiqueta) {
        Session session = null;
        Transaction transaction = null;
        Query query = null;

        try {
            session = HibernateUtil.openSession();
            transaction = session.beginTransaction();

            query = session.createQuery("from Etiqueta t where t.etiqueta ='"+etiqueta+"'");

            return (Etiqueta) query.uniqueResult();
        } catch (HibernateException e) {
            transaction.rollback();
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public List<Etiqueta> getAll() {
        return super.getAll();
    }

    @Override
    public void update(Etiqueta etiqueta) {
        super.update(etiqueta);
    }

    @Override
    public void deleteById(Etiqueta etiqueta) {
        etiqueta.setDeleted(true);
        this.update(etiqueta);
    }
}
