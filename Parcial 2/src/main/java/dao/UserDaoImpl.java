package dao;

import hibernate.HibernateUtil;
import modelo.Profile;
import modelo.Timeline;
import modelo.User;
import modelo.Wall;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static hibernate.HibernateUtil.getSession;

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

    public Profile getProfile(User user){

        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from Profile where user = :user").setParameter("user", user);
        Profile profile = (Profile) query.uniqueResult();

        return profile;
    }

    public User searchByUsername(String username){

        Session session = null;
        Transaction transaction = null;
        Query query = null;

        try {
            session = HibernateUtil.buildSessionFactory().openSession();
            transaction = session.beginTransaction();

            query = session.createQuery("from User where username = :username").setParameter("username", username);

            return (User) query.uniqueResult();

        } catch (HibernateException e) {
            transaction.rollback();
            return null;
        } finally {
            session.close();
        }
    }

    public User matchUsernameAndPassword(String username, String password)
            throws Exception {
        try
        {
            Query q = getSession().createQuery("from User where username = :username1 and password = :password1");

            q.setParameter("username1", username);
            q.setParameter("password1", password);
            User user = (User) q.uniqueResult();
            System.out.println(user.getId());

            return user;
        }
        catch (HibernateException e)
        {
            throw new Exception("No se pudo obtener el usuario: " + username, e);
        }
    }

    public List<User> getUsersById(List<Integer> userIds)
    {
        List<User> users;
        Session session = null;
        Transaction transaction = null;
        Query query = null;

        try {
            session = HibernateUtil.openSession();
            transaction = session.beginTransaction();
            if(userIds.size() > 0) {
                query = session.createQuery("from User where id in (:userIds)").setParameterList("userIds", userIds);

                users = (List<User>) query.list();
            }
            else {
                users = new ArrayList<User>();
            }
            return users;

        } catch (HibernateException e) {
            transaction.rollback();
            logger.debug("Error al ejecutar un select el objeto en la base de datos.", e);
            return null;
        } finally {
            session.close();
        }
    }

}
