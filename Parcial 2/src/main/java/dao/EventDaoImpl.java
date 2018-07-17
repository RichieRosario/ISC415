package dao;

import hibernate.HibernateUtil;
import modelo.Album;
import modelo.Event;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class EventDaoImpl extends Repositorio<Event, Integer> implements EventDao {

    private static final Logger logger = LoggerFactory.getLogger(EventDaoImpl.class);
    public EventDaoImpl(Class<Event> eventClass){

        super(eventClass);
    }

    public void add(Event event){
        super.add(event);
    }


    public Event findOne(Integer id) {
        return super.findOne(id);
    }

    public List<Event> getAll() {

        Session session = null;
        Transaction transaction = null;
        Query query = null;

        try {
            session = HibernateUtil.openSession();


            transaction = session.beginTransaction();

            query = session.createQuery("from Event a");

            return query.list();
        } catch (HibernateException e) {
            transaction.rollback();
            logger.debug("Error al ejecutar un select el objeto en la base de datos.", e);
            return null;
        } finally {
            session.close();
        }

    }

    public void update(Event event) {

        super.update(event);
    }

    public void deleteById(Event event) {
        event.setDeleted(true);
        this.update(event);
    }
}
