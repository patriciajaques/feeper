/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.model;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
 
public class HibernateUtil<T> {
    private static SessionFactory fabricaSessao;
    private static Session sessao;
    private static Transaction transacao;
    
    public HibernateUtil(){
        iniciarSessao();
    }
     
    /**
     * Inicia a SessionFactory
     */
    private void iniciarSessao(){        
        if(fabricaSessao == null)
            try {
                fabricaSessao = new AnnotationConfiguration().configure().buildSessionFactory();
            } catch (Exception e){
                System.err.println(e.fillInStackTrace());
            }
    }
     
    /**
     * Retorna uma nova sessão
     * @return Session
     */
    public Session getSessao(){
        return fabricaSessao.openSession();
    }
    
    public List<T> pesquisar(Class objClass, String coluna, String dado){
        List<T> lista = null;
        Query query = null;
        try {
            sessao = getSessao();
            transacao = sessao.beginTransaction();
            query = sessao.createQuery("From "+objClass.getName()+" Where "+coluna+" like '%"+dado+"%'");
            lista = query.list();
        } catch (HibernateException e) { 
            transacao.rollback();
            System.err.println(e.fillInStackTrace());
        } finally {
            sessao.close();
            return lista;
        }
    }

    /**
     * Retorna todos os registros da tabela (classe) informada
     * @param objClass
     * @return List<Object>
     */
    public List<T> selecionar(Class objClass){
        List<T> lista = null;
        Query query = null;
        try {
            sessao = getSessao();
            transacao = sessao.beginTransaction();
            query = sessao.createQuery("From "+objClass.getName());
            lista = query.list();
        } catch (HibernateException e) { 
            transacao.rollback();
            System.err.println(e.fillInStackTrace());
        } finally {
            sessao.close();
            return lista;
        }
    }
     
    /**
     * Retorna apenas um objeto referente a classe e id informados
     * @param objClass
     * @param id
     * @return Object
     */
    public T selecionar(Class objClass, Integer id){
        T objGet = null;
        try {
            sessao = getSessao();
            transacao = sessao.beginTransaction();
            objGet = (T)sessao.get(objClass, id);
        } catch (HibernateException e) { 
            transacao.rollback();
            System.err.println(e.fillInStackTrace());
        } finally {
            sessao.close();
            return objGet;
        }
    }
     
    /**
     * Persiste o objeto passado por parâmetro
     * @param obj 
     */
    public boolean inserir(T obj){
        if (obj == null) return false;
        try{
            sessao = getSessao();
            transacao = sessao.beginTransaction();
            sessao.save(obj);
            transacao.commit();
        } catch (HibernateException e) { 
            transacao.rollback();
            System.err.println(e.fillInStackTrace());
            return false;
        } finally {
            sessao.close();
            return true;
        }
    }
     
    /**
     * Atualiza o objeto passado por parâmetro
     * @param obj 
     */
    public boolean atualizar(T obj){
        if (obj == null) return false;
        try{
            sessao = getSessao();
            transacao = sessao.beginTransaction();
            sessao.update(obj);
            transacao.commit();
        } catch (HibernateException e) { 
            transacao.rollback();
            System.err.println(e.fillInStackTrace());
            return false;
        } finally {
            sessao.close();
            return true;
        }
    }
     
    /**
     * Exclui o objeto passado por parâmetro
     * @param obj 
     */
    public boolean excluir(T obj){
        if (obj == null) return false;
        try{
            sessao = getSessao();
            transacao = sessao.beginTransaction();
            sessao.delete(obj);
            transacao.commit();
        } catch (HibernateException e) { 
            transacao.rollback();
            System.err.println(e.fillInStackTrace());
            return false;
        } finally {
            sessao.close();
            return true;
        }
    }
    
}