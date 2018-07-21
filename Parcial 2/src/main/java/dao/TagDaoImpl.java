package dao;

import hibernate.HibernateUtil;
import modelo.Profile;
import modelo.Tag;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class TagDaoImpl extends Repositorio<Tag, Integer> implements TagDao {

    private static final Logger logger = LoggerFactory.getLogger(TagDaoImpl.class);
    public TagDaoImpl(Class<Tag> tagClass){

        super(tagClass);
    }

    public void add(Tag tag){
        super.add(tag);
    }


    public Tag findOne(Integer id) {
        return super.findOne(id);
    }

    public List<Tag> getAll() {

        Session session = null;
        Transaction transaction = null;
        Query query = null;

        try {
            session = HibernateUtil.openSession();


            transaction = session.beginTransaction();

            query = session.createQuery("from Tag a");

            return query.list();
        } catch (HibernateException e) {
            transaction.rollback();
            logger.debug("Error al ejecutar un select el objeto en la base de datos.", e);
            return null;
        } finally {
            session.close();
        }

    }

    public void update(Tag tag) {

        super.update(tag);
    }

    public void deleteById(Tag tag) {
        tag.setDeleted(true);
        this.update(tag);
    }

    public Tag searchByTag(String etiqueta) {
        Session session = null;
        Transaction transaction = null;
        Query query = null;

        try {
            session = HibernateUtil.openSession();
            transaction = session.beginTransaction();

            query = session.createQuery("from Tag where toUser = :etiqueta");

            return (Tag) query.uniqueResult();
        } catch (HibernateException e) {
            transaction.rollback();
            return null;
        } finally {
            session.close();
        }
    }
}
