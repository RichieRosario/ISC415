package dao;

import hibernate.HibernateUtil;
import modelo.Profile;
import modelo.Timeline;
import modelo.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    public Profile getProfile(int userId){

        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from Profile where user = :userId");
        query.setInteger("userId", userId);
        Profile profile = (Profile) query.uniqueResult();

        return profile;
    }

    public User searchByUsername(String username){

        User user = null;
        try
        {
            Query q = getSession().createQuery("from User where username = :username");
            q.setString("username", username);
            user = (User) q.uniqueResult();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return user;
    }

    public User matchUsernameAndPassword(String username, String password)
            throws Exception {
        try
        {
            Query q = getSession().createQuery("from User where username = :username1 and password = :password1");

            q.setString("username1", username);
            q.setString("password1", password);
            User user = (User) q.uniqueResult();
            System.out.println(user.getId());

            return user;
        }
        catch (HibernateException e)
        {
            throw new Exception("No se pudo obtener el usuario: " + username, e);
        }
    }


}
