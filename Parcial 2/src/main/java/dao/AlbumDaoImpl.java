package dao;

import modelo.Photo;
import org.hibernate.HibernateException;
import hibernate.HibernateUtil;
import modelo.Album;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;

import static hibernate.HibernateUtil.getSession;

public class AlbumDaoImpl extends Repositorio<Album, Integer> implements AlbumDao {

    private static final Logger logger = LoggerFactory.getLogger(AlbumDaoImpl.class);
    public AlbumDaoImpl(Class<Album> albumClass){

        super(albumClass);
    }

    public void add(Album album){
        super.add(album);
    }


    public Album findOne(Integer id) {
        return super.findOne(id);
    }

    public List<Album> getAll() {

        Session session = null;
        Transaction transaction = null;
        Query query = null;

        try {
            session = HibernateUtil.openSession();


            transaction = session.beginTransaction();

            query = session.createQuery("from Album a");

            return query.list();
        } catch (HibernateException e) {
            transaction.rollback();
            logger.debug("Error al ejecutar un select el objeto en la base de datos.", e);
            return null;
        } finally {
            session.close();
        }

    }

    public void update(Album album) {

        super.update(album);
    }

    public void deleteById(Album album) {
        album.setDeleted(true);
        this.update(album);
    }

}
