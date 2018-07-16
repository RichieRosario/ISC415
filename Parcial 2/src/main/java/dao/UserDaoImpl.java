package dao;

import hibernate.HibernateUtil;
import modelo.Timeline;
import modelo.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class UserDaoImpl extends Repositorio<User, Integer> implements UserDao {

    private static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);
    public UserDaoImpl(Class<User> userClass){

        super(userClass);
    }

    public void add(User user){
        super.add(user);
    }


    public User findOne(Integer id) {
        return super.findOne(id);
    }

    public List<User> getAll() {

        Session session = null;
        Transaction transaction = null;
        Query query = null;

        try {
            session = HibernateUtil.openSession();


            transaction = session.beginTransaction();

            query = session.createQuery("from User a");

            return query.list();
        } catch (HibernateException e) {
            transaction.rollback();
            logger.debug("Error al ejecutar un select el objeto en la base de datos.", e);
            return null;
        } finally {
            session.close();
        }

    }

    public void update(User user) {

        super.update(user);
    }

    public void deleteById(User user) {
        user.setDeleted(true);
        this.update(user);
    }
}
