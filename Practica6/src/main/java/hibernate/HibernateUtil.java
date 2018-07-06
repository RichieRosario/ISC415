package hibernate;

import java.util.logging.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.LoggerFactory;


public class HibernateUtil
{
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(HibernateUtil.class);
    public static SessionFactory sessionFactory = buildSessionFactory();
    public static Session session = null;

    /**
     * Funcion que contruye la sesion de hibernate para la interaccion con la base de datos
     *
     * @return
     */
    public static SessionFactory buildSessionFactory() {
        SessionFactory sessionFactory = null;
        try {
            Configuration configuration = new Configuration();
            configuration.configure("/public/hibernate.cfg.xml");
            sessionFactory = configuration.buildSessionFactory();
        } catch (ServiceException e) {
            logger.debug("No esta conectado a internet. Excepcion -> ", e);
        }
        return sessionFactory;
    }

    /**
     * Funcion que me devuelve la sessionfactory inicializada
     *
     * @return
     */
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static Session getSession() {
        return session;
    }

    /**
     * Funcion que me devuelve una nueva sesion abierta
     *
     * @return
     */
    public static Session openSession() {
        return getSessionFactory().openSession();
    }

    /**
     * Funcion que cierra la sesion abierta
     *
     * @return
     */
    public static void closeSession() {
        session.close();
    }
}
