package dao;

import hibernate.HibernateUtil;
import modelo.Album;
import modelo.Comment;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
}
