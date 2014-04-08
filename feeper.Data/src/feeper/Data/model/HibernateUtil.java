/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.Data.model;

import java.util.List;
import org.hibernate.CacheMode;
import org.hibernate.FlushMode;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.type.NullableType;
import org.hibernate.type.Type;
 
public class HibernateUtil<T> {
    //private static SessionFactory sessionFactory;
    private static Session session;
    private static Transaction transaction;
    private Class objClass;
    
    private static SessionFactory sessionFactory;
    
    static {
        try {
            // Create the SessionFactory from standard (hibernate.cfg.xml) 
            // config file.
            sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            // Log the exception. 
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    public HibernateUtil(Class objClass){
        //beginSession();
        this.objClass = objClass;
    }
     
    /**
     * Inicia a SessionFactory
     */
//    private void beginSession(){        
//        if(sessionFactory == null)
//            try {
//                sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
//            } catch (Exception e){
//                System.err.println(e.fillInStackTrace());
//            }
//    }
    
    public Session getSession(){
        session = sessionFactory.openSession();
        session.setCacheMode(CacheMode.IGNORE);
        return session;
    }
    
    public SQLQuery query(String sqlQuery)
    {
        session = getSession();
        session.setCacheMode(CacheMode.IGNORE);
        return session.createSQLQuery(sqlQuery);
    }
    
    public List<T> search(String coluna, String dado){
        List<T> lista = null;
        Query query = null;
        try {
            session = getSession();
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
            session = getSession();
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
            session = getSession();
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

    /**
     * Retorna todos os registros da tabela (classe) informada
     * @param objClass
     * @return List<Object>
     */
    public List<T> getAll(){
        List<T> lista = null;
        Query query = null;
        try {
            session = getSession();
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
        return getByColumn(column, value, Hibernate.STRING);
    }
    public List<T> getByColumn(String column, int value){
        return getByColumn(column, value, Hibernate.INTEGER);
    }
    private List<T> getByColumn(String column, Object value, Type type)
    {
        List<T> lista = null;
        Query query = null;
        try {
            session = getSession();
            //transacao = session.beginTransaction();
            query = session.createQuery("From "+objClass.getName()+" Where "+column+" = :p ");
            
            if (type == Hibernate.INTEGER)
            {
                query.setInteger("p", Integer.parseInt(value.toString()));
            }
            else if (type == Hibernate.STRING)
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
     
    /**
     * Retorna apenas um objeto referente a classe e id informados
     * @param objClass
     * @param id
     * @return Object
     */
    public T getById(Integer id){
        T objGet = null;
        try {
            session = getSession();
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
     
    /**
     * Persiste o objeto passado por parâmetro
     * @param obj 
     */
    public boolean insert(T obj){
        if (obj == null) return false;
        try{
            session = getSession();
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
            session.close();
        }
    }
    
    public boolean insertOrUpdate(T obj){
        if (obj == null) return false;
        try{
            session = getSession();
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
            session.close();
        }
    }
     
    /**
     * Atualiza o objeto passado por parâmetro
     * @param obj 
     */
    public boolean update(T obj){
        if (obj == null) return false;
        try{
            session = getSession();
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
            session.close();
        }
    }
     
    /**
     * Exclui o objeto passado por parâmetro
     * @param obj 
     */
    public boolean delete(T obj){
        if (obj == null) return false;
        try{
            session = getSession();
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
            session.close();
        }
    }
    
}