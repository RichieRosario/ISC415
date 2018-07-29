package dao;

import hibernate.HibernateUtil;
import modelo.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;

public class CommentDaoImpl extends Repositorio<Comment, Integer> implements CommentDao {

    private static final Logger logger = LoggerFactory.getLogger(CommentDaoImpl.class);
    public CommentDaoImpl(Class<Comment> commentClass){

        super(commentClass);
    }

    public void add(Comment comment){
        super.add(comment);
    }


    public Comment findOne(Integer id) {
        return super.findOne(id);
    }

    public List<Comment> getAll() {

        Session session = null;
        Transaction transaction = null;
        Query query = null;

        try {
            session = HibernateUtil.openSession();


            transaction = session.beginTransaction();

            query = session.createQuery("from Comment a");

            return query.list();
        } catch (HibernateException e) {
            transaction.rollback();
            logger.debug("Error al ejecutar un select el objeto en la base de datos.", e);
            return null;
        } finally {
            session.close();
        }

    }

    public void update(Comment comment) {

        super.update(comment);
    }

    public void deleteById(Comment comment) {
        comment.setDeleted(true);
        this.update(comment);
    }
//
//    public int addLike(int commentId, User user)
//    {
//        Session session = HibernateUtil.getSessionFactory().openSession();
//        Query query = session.createQuery("from Comment where id = :commentId");
//        query.setInteger("commentId", commentId);
//        Comment comment = (Comment) query.uniqueResult();
//        int likes = comment.getLikes() + 1;
//        try
//        {
//            Transaction transaction = session.beginTransaction();
//            comment.setLikes(likes);
//            session.update(comment);
//            transaction.commit();
//        }
//        catch(Exception e)
//        {
//            e.printStackTrace();
//        }
//        finally
//        {
//            session.close();
//        }
//
//        UserDaoImpl userDao = null;
//        Profile profile = userDao.getProfile(user);
//        String notification = "A " + profile.getNombre() + " " + profile.getApellido() + " le gusto tu comentario";
//        addNotification(user, notification, comment);
//
//        EventDaoImpl eventDao = null;
//        WallDaoImpl wallDao = null;
//        Wall wall = null;
//
//        Event event = new Event();
//        wall =  wallDao.findWallByUser(user);
//        event.setUser(user);
//        event.setEvento(notification);
//        event.setWall(wall);
//        event.setFecha(LocalDate.now());
//        eventDao.add(event);
//
//        return likes;
//    }
//    public void addNotification(User user, String notification, Comment comment)
//    {
//        Session session = HibernateUtil.getSessionFactory().openSession();
//        try
//        {
//            Notification n = new Notification();
//            n.setUser(user);
//            n.setToUser(comment.getUser());
//            n.setNotificacion(notification);
//            n.setSeen(false);
////            n.set(post);
////            post.getNotifications().add(n);
//
//            Transaction transaction = session.beginTransaction();
//            session.save(n);
//            transaction.commit();
//
//        }
//        catch(Exception e)
//        {
//            e.printStackTrace();
//        }
//        finally
//        {
//            session.close();
//        }
//
//    }
}
