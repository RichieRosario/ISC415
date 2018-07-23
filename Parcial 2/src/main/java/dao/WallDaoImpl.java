package dao;

import hibernate.HibernateUtil;
import modelo.Album;
import modelo.User;
import modelo.Wall;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static hibernate.HibernateUtil.getSession;

public class WallDaoImpl extends Repositorio<Wall, Integer> implements WallDao{

    private static final Logger logger = LoggerFactory.getLogger(WallDao.class);
    public WallDaoImpl(Class<Wall> wallClass){

        super(wallClass);
    }

    public void add(Wall wall){
        super.add(wall);
    }


    public Wall findOne(Integer id) {
        return super.findOne(id);
    }

    public List<Wall> getAll() {

        Session session = null;
        Transaction transaction = null;
        Query query = null;

        try {
            session = HibernateUtil.openSession();


            transaction = session.beginTransaction();

            query = session.createQuery("from Wall a");

            return query.list();
        } catch (HibernateException e) {
            transaction.rollback();
            logger.debug("Error al ejecutar un select el objeto en la base de datos.", e);
            return null;
        } finally {
            session.close();
        }

    }

    public void update(Wall wall) {

        super.update(wall);
    }

    public void deleteById(Wall wall) {
        wall.setDeleted(true);
        this.update(wall);
    }

    public Wall findWallByUser(User user){

        Session session = HibernateUtil.getSessionFactory().openSession();

        Query q = getSession().createQuery("from Wall where user = :user").setParameter("user", user);
        Wall wall = (Wall) q.uniqueResult();

        return wall;
    }
}
