package dao;

import hibernate.HibernateUtil;
import modelo.LikeDislike;
import modelo.Notification;
import modelo.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static hibernate.HibernateUtil.getSession;

public class NotificationDaoImpl extends Repositorio<Notification, Integer> implements NotificationDao {

    private static final Logger logger = LoggerFactory.getLogger(NotificationDaoImpl.class);
    public NotificationDaoImpl(Class<Notification> notificationClass){

        super(notificationClass);
    }

    public void add(Notification notification){
        super.add(notification);
    }


    public Notification findOne(Integer id) {
        return super.findOne(id);
    }

    public List<Notification> getAll() {

        Session session = null;
        Transaction transaction = null;
        Query query = null;

        try {
            session = HibernateUtil.openSession();


            transaction = session.beginTransaction();

            query = session.createQuery("from Notification a");

            return query.list();
        } catch (HibernateException e) {
            transaction.rollback();
            logger.debug("Error al ejecutar un select el objeto en la base de datos.", e);
            return null;
        } finally {
            session.close();
        }

    }

    public void update(Notification notification) {

        super.update(notification);
    }

    public void deleteById(Notification notification) {
        notification.setDeleted(true);
        this.update(notification);
    }

    public List<Notification> unseenNotifications(User user)
    {
        Session session = null;
        Transaction transaction = null;
        Query query = null;

        try {
            session = HibernateUtil.openSession();
            transaction = session.beginTransaction();
            query = session.createQuery("from Notification where user_id = :user and isSeen = false")
            .setParameter("user", user);

            List<Notification> list = query.list();

            return list;

        } catch (HibernateException e) {
            transaction.rollback();
            logger.debug("Error al ejecutar un select el objeto en la base de datos.", e);
            return null;
        } finally {
            session.close();
        }
    }
    public void markAsRead(User user)
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query q = session.createQuery("from Notification where toUser = :userId and isSeen = false ");
        q.setInteger("userId", user.getId());
        List<Notification> notificationList = (List<Notification>) q.list();
        try
        {
            Transaction transaction = session.beginTransaction();
            for(Notification n : notificationList)
            {
                n.setSeen(true);
                session.update(n);
            }
            transaction.commit();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            session.close();
        }
    }

}
