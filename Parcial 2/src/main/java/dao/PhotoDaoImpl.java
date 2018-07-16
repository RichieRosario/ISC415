package dao;

import hibernate.HibernateUtil;
import modelo.Notification;
import modelo.Photo;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class PhotoDaoImpl extends Repositorio<Photo, Integer> implements PhotoDao {

    private static final Logger logger = LoggerFactory.getLogger(PhotoDaoImpl.class);
    public PhotoDaoImpl(Class<Photo> photoClass){

        super(photoClass);
    }

    public void add(Photo photo){
        super.add(photo);
    }


    public Photo findOne(Integer id) {
        return super.findOne(id);
    }

    public List<Photo> getAll() {

        Session session = null;
        Transaction transaction = null;
        Query query = null;

        try {
            session = HibernateUtil.openSession();


            transaction = session.beginTransaction();

            query = session.createQuery("from Photo a");

            return query.list();
        } catch (HibernateException e) {
            transaction.rollback();
            logger.debug("Error al ejecutar un select el objeto en la base de datos.", e);
            return null;
        } finally {
            session.close();
        }

    }

    public void update(Photo photo) {

        super.update(photo);
    }

    public void deleteById(Photo photo) {
        photo.setDeleted(true);
        this.update(photo);
    }
}
