/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.Data.model;

import java.util.List;
import org.hibernate.CacheMode;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;

public class HibernateUtil<T> {

    private static final SessionFactory SESSION_FACTORY;
    private static final ServiceRegistry SERVICE_REGISTRY;

    private static StatelessSession session;
    private static Transaction transaction;
    private Class objClass;

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();
            SERVICE_REGISTRY = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
            SESSION_FACTORY = configuration.buildSessionFactory(SERVICE_REGISTRY);
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static StatelessSession currentSession() throws HibernateException {
        // Open a new Session, if this is none yet
        if (session == null) {
            session = SESSION_FACTORY.openStatelessSession();
        }
        return session;
    }

    public HibernateUtil(Class objClass) {
        this.objClass = objClass;
    }

    public SQLQuery query(String sqlQuery) {
        session = currentSession();

        SQLQuery query = session.createSQLQuery(sqlQuery);
        //query.setCacheable(false);
        return query;
    }

    public List<T> search(String coluna, String dado) {
        List<T> lista = null;
        try {
            SQLQuery query = query("Select * From " + objClass.getSimpleName() + " Where " + coluna + " like :like");
            query.setParameter("like", "%" + dado + "%");
            lista = query.list();
        } catch (HibernateException e) {
            System.err.println(e.fillInStackTrace());
        }
        return lista;
    }

    public List<Object> search(String colunaFiltro, String filtro, String colunasResultado) {
        List<Object> lista = null;
        try {
            SQLQuery query = query("Select " + colunasResultado + " From " + objClass.getSimpleName() + " Where " + colunaFiltro + " like :like order by " + colunasResultado);
            query.setParameter("like", "%" + filtro + "%");
            lista = query.list();
        } catch (HibernateException e) {
            System.err.println(e.fillInStackTrace());
        }
        return lista;
    }

    public List<Object> search(String colunaFiltro, String filtro, String where, String colunasResultado) {
        List<Object> lista = null;
        try {
            SQLQuery query = query("Select " + colunasResultado + " From " + objClass.getSimpleName() + " Where " + colunaFiltro + " like :like " + where + " order by " + colunasResultado);
            query.setParameter("like", "%" + filtro + "%");
            lista = query.list();
        } catch (HibernateException e) {
            System.err.println(e.fillInStackTrace());
        }
        return lista;
    }

    public List<T> getAll() {
        List<T> lista = null;
        try {
            SQLQuery query = query("Select * From " + objClass.getSimpleName());
            lista = query.list();
        } catch (HibernateException e) {
            System.err.println(e.fillInStackTrace());
        }
        return lista;
    }

    public List<T> getByColumn(String column, String value) {
        return getByColumn(column, value, StringType.INSTANCE);
    }

    public List<T> getByColumn(String column, int value) {
        return getByColumn(column, value, IntegerType.INSTANCE);
    }

    private List<T> getByColumn(String column, Object value, Type type) {
        List<T> lista = null;
        Query query = null;
        try {
            session = currentSession();
            query = session.createQuery("From " + objClass.getSimpleName() + " Where " + column + " = :p ");

            if (type == IntegerType.INSTANCE) {
                query.setInteger("p", Integer.parseInt(value.toString()));
            } else if (type == StringType.INSTANCE) {
                query.setString("p", value.toString());
            }

            lista = query.list();
        } catch (HibernateException e) {
            System.err.println(e.fillInStackTrace());
        }
        return lista;
    }

    public T getById(Integer id) {
        T objGet = null;
        try {
            session = currentSession();
            objGet = (T) session.get(objClass, id);
        } catch (HibernateException e) {
            System.err.println(e.fillInStackTrace());
        }
        return objGet;
    }

    public boolean insert(T obj) {
        if (obj == null) {
            return false;
        }
        try {
            session = currentSession();
            transaction = session.beginTransaction();
            session.insert(obj);
            transaction.commit();
            return true;
        } catch (HibernateException e) {
            transaction.rollback();
            System.err.println(e.fillInStackTrace());
            return false;
        }
    }

    public boolean update(T obj) {
        if (obj == null) {
            return false;
        }
        try {
            session = currentSession();
            transaction = session.beginTransaction();
            session.update(obj);
            transaction.commit();
            return true;
        } catch (HibernateException e) {
            transaction.rollback();
            System.err.println(e.fillInStackTrace());
            return false;
        }
    }

    public boolean delete(T obj) {
        if (obj == null) {
            return false;
        }
        try {
            session = currentSession();
            transaction = session.beginTransaction();
            session.delete(obj);
            transaction.commit();
            return true;
        } catch (HibernateException e) {
            transaction.rollback();
            System.err.println(e.fillInStackTrace());
            return false;
        }
    }
}
