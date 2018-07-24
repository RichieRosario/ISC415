package dao;

import hibernate.HibernateUtil;
import modelo.Comment;
import modelo.Friendship;
import modelo.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.ArrayList;

import static hibernate.HibernateUtil.getSession;

public class FriendshipDaoImpl extends Repositorio<Friendship, Integer> implements FriendshipDao {

    private static final Logger logger = LoggerFactory.getLogger(FriendshipDaoImpl.class);
    public FriendshipDaoImpl(Class<Friendship> friendshipClass){

        super(friendshipClass);
    }

    public void add(Friendship friendship){
        super.add(friendship);
    }


    public Friendship findOne(Integer id) {
        return super.findOne(id);
    }

    public List<Friendship> getAll() {

        Session session = null;
        Transaction transaction = null;
        Query query = null;

        try {
            session = HibernateUtil.openSession();


            transaction = session.beginTransaction();

            query = session.createQuery("from Friendship a");

            return query.list();
        } catch (HibernateException e) {
            transaction.rollback();
            logger.debug("Error al ejecutar un select el objeto en la base de datos.", e);
            return null;
        } finally {
            session.close();
        }

    }

    public void update(Friendship friendship) {

        super.update(friendship);
    }

    public void deleteById(Friendship friendship) {
        friendship.setDeleted(true);
        this.update(friendship);
    }


    public List<Integer> getAllFriends(User user)
    {
        List<Integer> list = new ArrayList<>();
        Session session = null;
        Transaction transaction = null;
        Query query = null;

        try {
            session = HibernateUtil.openSession();
            transaction = session.beginTransaction();
            query = session.createQuery("from Friendship where (fromUser = :userid or toUser = :userid) and isAccepted = true")
                    .setParameter("userid", user.getId());

            List<Friendship> friendList = query.getResultList();

            for(Friendship f : friendList)
        {
            if(f.getFromUser() != user.getId())
            {
                list.add(f.getFromUser());
            }
            if(f.getToUser() != user.getId())
            {
                list.add(f.getToUser());
            }
        }

            return list;

        } catch (HibernateException e) {
            transaction.rollback();
            logger.debug("Error al ejecutar un select el objeto en la base de datos.", e);
            return null;
        } finally {
            session.close();
        }
//        query = session.createQuery("from Friendship where (fromUser = :userid or toUser = :userid) and isAccepted = true")
//                .setParameter("userid", user.getId());
//        List<Friendship> friendList = (ArrayList<Friendship>) query.list();
//        List<Integer> friendIds = new ArrayList<Integer>();
//
//        for(Friendship f : friendList)
//        {
//            if(f.getFromUser() != user.getId())
//            {
//                friendIds.add(f.getFromUser());
//            }
//            if(f.getToUser() != user.getId())
//            {
//                friendIds.add(f.getToUser());
//            }
//        }
//
//        return friendIds;
    }
    public List<Integer> getFriendRequests(User user)
    {
        Session session = null;
        Transaction transaction = null;
        Query query = null;
        List<Integer> list = new ArrayList<>();

        try {
            session = HibernateUtil.openSession();
            transaction = session.beginTransaction();
            query = session.createQuery("select fromUser from Friendship where toUser = :userid and isAccepted = false ")
                    .setParameter("userid", user.getId());
            for (Object object : query.list()) {

                list.add(Integer.parseInt(object.toString()));
            }

            return list;

        } catch (HibernateException e) {
            transaction.rollback();
            logger.debug("Error al ejecutar un select el objeto en la base de datos.", e);
            return null;
        } finally {
            session.close();
        }

    }
    public List<Integer> getPendingRequests(User user)
    {
        Session session = null;
        Transaction transaction = null;
        Query query = null;
        List<Integer> list = new ArrayList<>();

        try {
            session = HibernateUtil.openSession();
            transaction = session.beginTransaction();
            query = session.createQuery("select toUser from Friendship where fromUser = :userid and isAccepted = false ")
                    .setParameter("userid", user.getId());
            for (Object object : query.list()) {

                list.add(Integer.parseInt(object.toString()));
            }

            return list;

        } catch (HibernateException e) {
            transaction.rollback();
            logger.debug("Error al ejecutar un select el objeto en la base de datos.", e);
            return null;
        } finally {
            session.close();
        }
    }
    public void sendFriendRequest(User user, int userId)
    {
        Friendship friend = new Friendship();
        friend.setFromUser(user.getId());
        friend.setToUser(userId);
        friend.setAccepted(false);
        Session session = HibernateUtil.getSessionFactory().openSession();
        try
        {
            Transaction transaction = session.beginTransaction();
            session.save(friend);
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
    public void acceptRequest(User user, int personId)
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query q = session.createQuery("from Friendship where fromUser = :personId and toUser = :userId");
        q.setParameter("personId", personId);
        q.setParameter("userId", user.getId());
        Friendship friend = (Friendship) q.uniqueResult();
        friend.setAccepted(true);
        Transaction transaction = session.beginTransaction();
        session.update(friend);
        transaction.commit();
        session.close();
    }
    public void unFriend(User user, int personId)
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try
        {
            Transaction transaction = session.beginTransaction();
            Query q = session.createQuery("delete from Friendship where (toUser = :userid or fromUser = :userid) and (toUser = :personid or fromUser = :personid)");
            q.setInteger("userid", user.getId());
            q.setInteger("personid", personId);
            int result = q.executeUpdate();
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
    public boolean checkIfFriend(User user, int userId)
    {
        List<Integer> friendIds = getAllFriends(user);
        for(int f : friendIds)
        {
            if(userId == f)
                return true;
        }

        return false;
    }


    public boolean checkIfFriend2(User user, int userId)
    {
        List<Friendship> friendships = getAll();

        List<Integer> friendIds = new ArrayList<>();

        for(Friendship fr : friendships){
            friendIds.add(fr.getFromUser());
        }

        for(int f : friendIds)
        {
            if(userId == f)
                return true;
        }

        return false;
    }
}
