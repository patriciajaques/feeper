/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.model;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
 
public class HibernateUtil<T> {
    private static SessionFactory sessionFactory;
    private static Session session;
    private static Transaction transaction;
    private Class objClass;
    
    public HibernateUtil(Class objClass){
        beginSession();
        this.objClass = objClass;
    }
     
    /**
     * Inicia a SessionFactory
     */
    private void beginSession(){        
        if(sessionFactory == null)
            try {
                sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
            } catch (Exception e){
                System.err.println(e.fillInStackTrace());
            }
    }
     
    /**
     * Retorna uma nova sessão
     * @return Session
     */
    public Session getSession(){
        return sessionFactory.openSession();
    }
    
    public SQLQuery query(String sqlQuery)
    {
        session = getSession();
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
            query = session.createQuery("Select "+colunasResultado+" From "+objClass.getName()+" Where "+colunaFiltro+" like :like ");
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
            session.close();
        }
    }
    
    public boolean insertOrUpdate(T obj){
        if (obj == null) return false;
        try{
            session = getSession();
            transaction = session.beginTransaction();
            session.saveOrUpdate(obj);
            session.refresh(obj);
            transaction.commit();
            return true;
        } catch (HibernateException e) { 
            transaction.rollback();
            System.err.println(e.fillInStackTrace());
            return false;
        } finally {
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
            session.update(obj);
            transaction.commit();
            return true;
        } catch (HibernateException e) { 
            transaction.rollback();
            System.err.println(e.fillInStackTrace());
            return false;
        } finally {
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
            session.close();
        }
    }
    
}