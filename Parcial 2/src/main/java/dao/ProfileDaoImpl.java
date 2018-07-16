package dao;

import hibernate.HibernateUtil;
import modelo.Post;
import modelo.Profile;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ProfileDaoImpl extends Repositorio<Profile, Integer> implements ProfileDao {

    private static final Logger logger = LoggerFactory.getLogger(ProfileDaoImpl.class);
    public ProfileDaoImpl(Class<Profile> profileClass){

        super(profileClass);
    }

    public void add(Profile profile){
        super.add(profile);
    }


    public Profile findOne(Integer id) {
        return super.findOne(id);
    }

    public List<Profile> getAll() {

        Session session = null;
        Transaction transaction = null;
        Query query = null;

        try {
            session = HibernateUtil.openSession();


            transaction = session.beginTransaction();

            query = session.createQuery("from Profile a");

            return query.list();
        } catch (HibernateException e) {
            transaction.rollback();
            logger.debug("Error al ejecutar un select el objeto en la base de datos.", e);
            return null;
        } finally {
            session.close();
        }

    }

    public void update(Profile profile) {

        super.update(profile);
    }

    public void deleteById(Profile profile) {
        profile.setDeleted(true);
        this.update(profile);
    }
}
