package dao;

import encapsulacion.Usuario;
import hibernate.HibernateUtil;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import servicios.Hash;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Sql2oUsuarioDao extends Repositorio<Usuario, Long> implements UsuariosDao{

    private static final Logger logger = LoggerFactory.getLogger(Sql2oUsuarioDao.class);

    public Sql2oUsuarioDao(Class<Usuario> usuarioClass) {
        super(usuarioClass);
    }

    @Override
    public void add(Usuario usuario){


        super.add(usuario);

    }

    @Override
    public Usuario findOne(Long aLong) {
        return super.findOne(aLong);
    }

    @Override
    public List<Usuario> getAll() {
        return super.getAll();
    }

    @Override
    public void update(Usuario usuario) {

        super.update(usuario);
    }

    @Override
    public void deleteById(Usuario usuario) {

        usuario.setDeleted(true);
        this.update(usuario);
    }

    @Override
    public Usuario searchByUsername(String username){

        Session session = null;
        Transaction transaction = null;
        Query query = null;

        try {
            session = HibernateUtil.buildSessionFactory().openSession();
            transaction = session.beginTransaction();

            query = session.createQuery("from Usuario where username = :username").setParameter("username", username);

            return (Usuario) query.uniqueResult();

        } catch (HibernateException e) {
            transaction.rollback();
            logger.debug("Error al ejecutar un select el objeto en la base de datos.", e);
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public Usuario searchById(Long id){

        Session session = null;
        Transaction transaction = null;
        Query query = null;

        try {
            session = HibernateUtil.buildSessionFactory().openSession();
            transaction = session.beginTransaction();

            query = session.createQuery("from Usuario where id = :id").setParameter("id", id);

            return (Usuario) query.uniqueResult();

        } catch (HibernateException e) {
            transaction.rollback();
            logger.debug("Error al ejecutar un select el objeto en la base de datos.", e);
            return null;
        } finally {
            session.close();
        }
    }


}
