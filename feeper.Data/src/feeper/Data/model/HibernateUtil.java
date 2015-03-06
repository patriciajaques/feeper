/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.Data.model;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;

public class HibernateUtil<T> {

    private static final SessionFactory SESSION_FACTORY;

    private Class objClass;

    static {
        try {

            Configuration configuration = new Configuration();
            configuration.configure();
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
            SESSION_FACTORY = configuration.buildSessionFactory(serviceRegistry);

        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public Session currentSession() throws HibernateException {
        return SESSION_FACTORY.getCurrentSession();
    }

    public HibernateUtil(Class objClass) {
        this.objClass = objClass;
    }

    public List<T> search(String coluna, String dado) {
        List<T> lista = null;
        Transaction transaction = currentSession().beginTransaction();
        try {
            SQLQuery query = currentSession().createSQLQuery("Select * From " + objClass.getSimpleName() + " Where " + coluna + " like :like");
            query.setParameter("like", "%" + dado + "%");
            lista = query.list();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            System.err.println(e.fillInStackTrace());
        }
        return lista;
    }

    public List<Object> search(String colunaFiltro, String filtro, String colunasResultado) {
        List<Object> lista = null;
        Transaction transaction = currentSession().beginTransaction();
        try {

            SQLQuery query = currentSession().createSQLQuery("Select " + colunasResultado + " From " + objClass.getSimpleName() + " Where " + colunaFiltro + " like :like order by " + colunasResultado);
            query.setParameter("like", "%" + filtro + "%");
            lista = query.list();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            System.err.println(e.fillInStackTrace());
        }
        return lista;
    }

    public List<Object> search(String colunaFiltro, String filtro, String where, String colunasResultado) {
        List<Object> lista = null;
        Transaction transaction = currentSession().beginTransaction();
        try {

            SQLQuery query = currentSession().createSQLQuery("Select " + colunasResultado + " From " + objClass.getSimpleName() + " Where " + colunaFiltro + " like :like " + where + " order by " + colunasResultado);
            query.setParameter("like", "%" + filtro + "%");
            lista = query.list();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            System.err.println(e.fillInStackTrace());
        }
        return lista;
    }

    public List<T> getAll() {
        List<T> lista = null;
        Transaction transaction = currentSession().beginTransaction();
        try {

            SQLQuery query = currentSession().createSQLQuery("Select * From " + objClass.getSimpleName());
            lista = query.list();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
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
        Transaction transaction = currentSession().beginTransaction();
        try {
            query = currentSession().createQuery("From " + objClass.getSimpleName() + " Where " + column + " = :p ");

            if (type == IntegerType.INSTANCE) {
                query.setInteger("p", Integer.parseInt(value.toString()));
            } else if (type == StringType.INSTANCE) {
                query.setString("p", value.toString());
            }

            lista = query.list();
            transaction.commit();
        } catch (HibernateException e) {
            System.err.println(e.fillInStackTrace());
            transaction.rollback();
        }
        return lista;
    }

    public T getById(Integer id) {
        T objGet = null;
        Transaction transaction = currentSession().beginTransaction();
        try {
            objGet = (T) currentSession().get(objClass, id);
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            System.err.println(e.fillInStackTrace());
        }
        return objGet;
    }

    public boolean insert(T obj) {
        if (obj == null) {
            return false;
        }

        Transaction transaction = currentSession().beginTransaction();
        try {
            currentSession().save(obj);
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

        Transaction transaction = currentSession().beginTransaction();
        try {
            currentSession().update(obj);
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

        Transaction transaction = currentSession().beginTransaction();
        try {
            currentSession().delete(obj);
            transaction.commit();
            return true;
        } catch (HibernateException e) {
            transaction.rollback();
            System.err.println(e.fillInStackTrace());
            return false;
        }
    }
}
