package dao;

import hibernate.HibernateUtil;
import javafx.geometry.Pos;
import modelo.Photo;
import modelo.Post;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class PostDaoImpl extends Repositorio<Post, Integer> implements PostDao {

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
}
