package hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.spi.ServiceException;


public class HibernateUtil
{
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
            configuration.configure("hibernate.cfg.xml");
            sessionFactory = configuration.buildSessionFactory();
        } catch (ServiceException e) {
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
