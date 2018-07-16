package dao;

import hibernate.HibernateUtil;
import modelo.Comment;
import modelo.Friendship;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

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
}
