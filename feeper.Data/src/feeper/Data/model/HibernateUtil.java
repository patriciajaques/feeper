/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.Data.model;

import java.util.List;
import org.hibernate.CacheMode;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;
 
public class HibernateUtil<T> {
    public static final ThreadLocal MAP = new ThreadLocal();    
    private static final SessionFactory SESSION_FACTORY;
    
    private static Session session;
    private static Transaction transaction;
    private Class objClass;
    
    
    
    static {
        try {
            SESSION_FACTORY = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    public static Session currentSession() throws HibernateException {
        Session s = (Session)MAP.get();
        // Open a new Session, if this Thread has none yet
        if (s == null) {
            s = SESSION_FACTORY.openSession();
            MAP.set(s);
        }
        return s;
    }

    public static void closeSession() throws HibernateException {
        Session s = (Session)MAP.get();
        MAP.set(null);
        if (s != null) {
            s.close();
        }
    }
    
    public HibernateUtil(Class objClass){
        this.objClass = objClass;
    }
     
    public SQLQuery query(String sqlQuery)
    {
        session = currentSession();
        return session.createSQLQuery(sqlQuery);
    }
    
    public List<T> search(String coluna, String dado){
        List<T> lista = null;
        Query query = null;
        try {
            session = currentSession();
            //transacao = session.beginTransaction();
            query = session.createQuery("From "+objClass.getName()+" Where "+coluna+" like '%"+dado+"%'");
            lista = query.list();
        } catch (HibernateException e) { 
            //transacao.rollback();
            System.err.println(e.fillInStackTrace());
        } finally {
            //sessao.close();
            return lista;
        }
    }
    
    public List<Object> search(String colunaFiltro, String filtro, String colunasResultado){
        List<Object> lista = null;
        Query query = null;
        try {
            session = currentSession();
            //transacao = session.beginTransaction();
            query = session.createQuery("Select "+colunasResultado+" From "+objClass.getName()+" Where "+colunaFiltro+" like :like order by " + colunasResultado);
            query.setParameter("like", "%" + filtro + "%");
            lista = query.list();
        } catch (HibernateException e) { 
            //transacao.rollback();
            System.err.println(e.fillInStackTrace());
        } finally {
            //sessao.close();
            return lista;
        }
    }
    
    public List<Object> search(String colunaFiltro, String filtro, String where, String colunasResultado){
        List<Object> lista = null;
        Query query = null;
        try {
            session = currentSession();
            //transacao = session.beginTransaction();
            query = session.createQuery("Select "+colunasResultado+" From "+objClass.getName()+" Where "+colunaFiltro+" like :like " + where + " order by " + colunasResultado);
            query.setParameter("like", "%" + filtro + "%");
            lista = query.list();
        } catch (HibernateException e) { 
            //transacao.rollback();
            System.err.println(e.fillInStackTrace());
        } finally {
            //sessao.close();
            return lista;
        }
    }

    public List<T> getAll(){
        List<T> lista = null;
        Query query = null;
        try {
            session = currentSession();
            //transacao = session.beginTransaction();
            query = session.createQuery("From "+objClass.getName());
            lista = query.list();
        } catch (HibernateException e) { 
            //transacao.rollback();
            System.err.println(e.fillInStackTrace());
        } finally {
            //sessao.close();
            return lista;
        }
    }
    
    public List<T> getByColumn(String column, String value){
        return getByColumn(column, value, StringType.INSTANCE);
    }
    public List<T> getByColumn(String column, int value){
        return getByColumn(column, value, IntegerType.INSTANCE);
    }
    private List<T> getByColumn(String column, Object value, Type type)
    {
        List<T> lista = null;
        Query query = null;
        try {
            session = currentSession();
            //transacao = session.beginTransaction();
            query = session.createQuery("From "+objClass.getName()+" Where "+column+" = :p ");
            
            if (type == IntegerType.INSTANCE)
            {
                query.setInteger("p", Integer.parseInt(value.toString()));
            }
            else if (type == StringType.INSTANCE)
            {
                query.setString("p", value.toString());
            }
            
            lista = query.list();
        } catch (HibernateException e) { 
            //transacao.rollback();
            System.err.println(e.fillInStackTrace());
        } finally {
            //sessao.close();
            return lista;
        }
    }
     
    public T getById(Integer id){
        T objGet = null;
        try {
            session = currentSession();
            //transacao = session.beginTransaction();
            objGet = (T)session.get(objClass, id);
        } catch (HibernateException e) { 
            //transacao.rollback();
            System.err.println(e.fillInStackTrace());
        } finally {
            //sessao.close();
            return objGet;
        }
    }
     
    public boolean insert(T obj){
        if (obj == null) return false;
        try{
            session = currentSession();
            transaction = session.beginTransaction();
            session.save(obj);
            session.refresh(obj);
            transaction.commit();
            return true;
        } catch (HibernateException e) { 
            transaction.rollback();
            System.err.println(e.fillInStackTrace());
            return false;
        } finally {
            //session.clear();
            closeSession();
        }
    }
    
    public boolean insertOrUpdate(T obj){
        if (obj == null) return false;
        try{
            session = currentSession();
            transaction = session.beginTransaction();
            session.saveOrUpdate(obj);
            
            transaction.commit();
            
            session.refresh(obj);
            return true;
        } catch (HibernateException e) { 
            transaction.rollback();
            System.err.println(e.fillInStackTrace());
            return false;
        } finally {
            //session.clear();
            closeSession();
        }
    }
     
    public boolean update(T obj){
        if (obj == null) return false;
        try{
            session = currentSession();
            transaction = session.beginTransaction();
            //session.update(obj);
            session.merge(obj);
            transaction.commit();
            return true;
        } catch (HibernateException e) { 
            transaction.rollback();
            System.err.println(e.fillInStackTrace());
            return false;
        } finally {
            //session.clear();
            closeSession();
        }
    }
     
    public boolean delete(T obj){
        if (obj == null) return false;
        try{
            session = currentSession();
            transaction = session.beginTransaction();
            session.delete(obj);
            transaction.commit();
            return true;
        } catch (HibernateException e) { 
            transaction.rollback();
            System.err.println(e.fillInStackTrace());
            return false;
        } finally {
            //session.clear();
            closeSession();
        }
    }
    
}