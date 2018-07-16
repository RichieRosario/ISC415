package dao;

import hibernate.HibernateUtil;
import modelo.Tag;
import modelo.Timeline;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class TimelineDaoImpl extends Repositorio<Timeline, Integer> implements TimelineDao {

    private static final Logger logger = LoggerFactory.getLogger(TimelineDaoImpl.class);
    public TimelineDaoImpl(Class<Timeline> timelineClass){

        super(timelineClass);
    }

    public void add(Timeline timeline){
        super.add(timeline);
    }


    public Timeline findOne(Integer id) {
        return super.findOne(id);
    }

    public List<Timeline> getAll() {

        Session session = null;
        Transaction transaction = null;
        Query query = null;

        try {
            session = HibernateUtil.openSession();


            transaction = session.beginTransaction();

            query = session.createQuery("from Timeline a");

            return query.list();
        } catch (HibernateException e) {
            transaction.rollback();
            logger.debug("Error al ejecutar un select el objeto en la base de datos.", e);
            return null;
        } finally {
            session.close();
        }

    }

    public void update(Timeline timeline) {

        super.update(timeline);
    }

    public void deleteById(Timeline timeline) {
        timeline.setDeleted(true);
        this.update(timeline);
    }
}
