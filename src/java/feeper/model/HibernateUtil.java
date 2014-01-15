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
 
public abstract class HibernateUtil {
    private static SessionFactory fabricaSessao;
    private static Session sessao;
    private static Transaction transacao;
     
    /**
     * Inicia a SessionFactory
     */
    public static void iniciarSessao(){        
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
    private static Session getSessao(){
        return fabricaSessao.openSession();
    }

    /**
     * Retorna todos os registros da tabela (classe) informada
     * @param objClass
     * @return List<Object>
     */
    public static List<Object> selecionar(Class objClass){
        List<Object> lista = null;
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
    public static Object selecionar(Class objClass, long id){
        Object objGet = null;
        try {
            sessao = getSessao();
            transacao = sessao.beginTransaction();
            objGet = sessao.get(objClass, id);
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
    public static boolean inserir(Object obj){
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
    public static boolean atualizar(Object obj){
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
    public static boolean excluir(Object obj){
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