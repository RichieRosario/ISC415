package dao;

import hibernate.HibernateUtil;
import modelo.Friendship;
import modelo.LikeDislike;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class LikeDislikeDaoImpl extends Repositorio<LikeDislike, Integer> implements LikeDislikeDao {

    private static final Logger logger = LoggerFactory.getLogger(LikeDislikeDaoImpl.class);
    public LikeDislikeDaoImpl(Class<LikeDislike> likeDislikeClass){

        super(likeDislikeClass);
    }

    public void add(LikeDislike likeDislike){
        super.add(likeDislike);
    }


    public LikeDislike findOne(Integer id) {
        return super.findOne(id);
    }

    public List<LikeDislike> getAll() {

        Session session = null;
        Transaction transaction = null;
        Query query = null;

        try {
            session = HibernateUtil.openSession();


            transaction = session.beginTransaction();

            query = session.createQuery("from LikeDislike a");

            return query.list();
        } catch (HibernateException e) {
            transaction.rollback();
            logger.debug("Error al ejecutar un select el objeto en la base de datos.", e);
            return null;
        } finally {
            session.close();
        }

    }

    public void update(LikeDislike likeDislike) {

        super.update(likeDislike);
    }

    public void deleteById(LikeDislike likeDislike) {
        likeDislike.setDeleted(true);
        this.update(likeDislike);
    }
}
