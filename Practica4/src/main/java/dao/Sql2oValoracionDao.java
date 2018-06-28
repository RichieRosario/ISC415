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

    private static final Logger logger = LoggerFactory.getLogger(Sql2oValoracionDao.class);

    public Sql2oValoracionDao(Class<Valoracion> valoracionClass) {
        super(valoracionClass);
    }

    public void add(Valoracion valoracion){

        super.add(valoracion);

    }

    public Valoracion findOne(Long id) {

        return super.findOne(id);
    }

    public List<Valoracion> getAll() { return super.getAll();}



    public void update(Valoracion valoracion) {

        super.update(valoracion);
    }

    public void deleteById(Valoracion valoracion) {
        valoracion.setDeleted(true);
        this.update(valoracion);
    }





}
