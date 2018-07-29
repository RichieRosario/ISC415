package dao;

import hibernate.HibernateUtil;
import javafx.geometry.Pos;
import modelo.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static hibernate.HibernateUtil.getSession;

public class PostDaoImpl extends Repositorio<Post, Integer> implements PostDao {

    private static List<Integer> friends;
    private static final Logger logger = LoggerFactory.getLogger(PostDaoImpl.class);
    public PostDaoImpl(Class<Post> postClass){

        super(postClass);
    }

    public void add(Post post){
        super.add(post);
    }


    public Post findOne(Integer id) {
        return super.findOne(id);
    }

    public List<Post> getAll() {

        Session session = null;
        Transaction transaction = null;
        Query query = null;

        try {
            session = HibernateUtil.openSession();


            transaction = session.beginTransaction();

            query = session.createQuery("from Post a");

            return query.list();
        } catch (HibernateException e) {
            transaction.rollback();
            logger.debug("Error al ejecutar un select el objeto en la base de datos.", e);
            return null;
        } finally {
            session.close();
        }

    }

    public void update(Post post) {

        super.update(post);
    }

    public void deleteById(Post post) {
        post.setDeleted(true);
        this.update(post);
    }

    public List<Post> getFriendPosts(User user, List<Integer> friendList)
    {
        friendList.add(user.getId());
        Query query = getSession().createQuery("from Post where user in (:personid) order by fecha desc");
        query.setParameterList("personid", friendList);
        List<Post> postList = (List<Post>) query.list();
        friends = friendList;
        return postList;
    }
    public List<Post> getMyPosts(User user)
    {
        Session session = null;
        Transaction transaction = null;
        Query query = null;

        try {
            session = HibernateUtil.openSession();
            transaction = session.beginTransaction();
            query = session.createQuery("from Post where user = :user").setParameter("user", user).setParameter("user", user);

            return query.list();
        } catch (HibernateException e) {
            transaction.rollback();
            logger.debug("Error al ejecutar un select el objeto en la base de datos.", e);
            return null;
        } finally {
            session.close();
        }

    }
//    public int addLike(int postId, User user)
//    {
//        Session session = HibernateUtil.getSessionFactory().openSession();
//        Query query = session.createQuery("from Post where id = :postId");
//        query.setInteger("postId", postId);
//        Post post = (Post) query.uniqueResult();
//        int likes = post.getLikes() + 1;
//        try
//        {
//            Transaction transaction = session.beginTransaction();
//            post.setLikes(likes);
//            session.update(post);
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
//        String notification = "A " + profile.getNombre() + " " + profile.getApellido() + " le gusto tu publicacion";
//        addNotification(user, notification, post);
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
//
//    public void addNotification(User user, String notification, Post post)
//    {
//        Session session = HibernateUtil.getSessionFactory().openSession();
//        try
//        {
//            Notification n = new Notification();
//            n.setUser(user);
//            n.setToUser(post.getUser());
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
    public String addComments(int postId, String comment, User user)
    {
       // String safe_comment = StringEscapeUtils.escapeHtml(comment);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query q = session.createQuery("from Post where id = :postId");
        q.setInteger("postId", postId);
        Post post = (Post) q.uniqueResult();
        try
        {
            Comment c = new Comment();
            c.setComentario(comment);
            c.setUser(user);
            //c.s(new Date());
            c.setPost(post);
            post.getComments().add(c);

            Transaction transaction = session.beginTransaction();

            session.update(post);

            transaction.commit();
        }
        catch(Exception e)
        {
            System.out.println("Could not PostDao");
            e.printStackTrace();
        }
        finally
        {
            session.close();
        }

        UserDaoImpl userDao = null;
        Profile profile = userDao.getProfile(user);

//        String notification = profile.getNombre() + " " + profile.getApellido() + " comento en tu post";
//        addNotification(user, notification, post);
//
//        String html = "<a href='profile.html'><img src='" + user.getPerson().getProfilePicPath() + "' width=20px class='img-circle pull-left' />" +
//                "<h5>&nbsp; " + user.getPerson().getFirstName() + " " + user.getPerson().getLastName() + "</a></h5>" +
//                "<p>" + comment + "</p>";
        String html = "Hola";
        return html;
    }

}
