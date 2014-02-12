/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.model;

import feeper.entity.CodigoFonte;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class CodigoFonteService extends HibernateUtil<CodigoFonte> {
    
    public static final String CODIGO_PADRAO_PRINCIPAL = "/* package qualquer; // Não coloque nome no package */\n" +
                                                        "\n" +
                                                        "import java.util.*;\n" +
                                                        "import java.lang.*;\n" +
                                                        "import java.io.*;\n" +
                                                        "\n" +
                                                        "/* O nome da classe deve ser \"Solution\" */\n" +
                                                        "class Solution\n" +
                                                        "{\n" +
                                                        "    public static void main (String[] args) throws java.lang.Exception\n" +
                                                        "    {\n" +
                                                        "        // Coloque aqui o seu código\n" +
                                                        "    }\n" +
                                                        "}";
    public static final String CODIGO_PADRAO_CLASSE = "/* package qualquer; // Não coloque nome no package */\n" +
                                                        "\n" +
                                                        "import java.util.*;\n" +
                                                        "import java.lang.*;\n" +
                                                        "import java.io.*;\n" +
                                                        "\n" +
                                                        "class #@#CLASSE#@#\n" +
                                                        "{\n" +
                                                        "   // Coloque aqui o seu código\n" +
                                                        "}";
    
    public CodigoFonteService() {
        super(CodigoFonte.class);
    }
    
    public List<CodigoFonte> getAllByIdExercicio(int idExercicio, int idAutor)
    {
        try {
            
            SQLQuery query = query("select * from CodigoFonte where IdExercicio = :idExercicio and IdAutor = :idAutor order by Principal desc ").addEntity(CodigoFonte.class);
            query.setInteger("idExercicio", idExercicio);
            query.setInteger("idAutor", idAutor);
            return query.list();
        } catch (Exception e) {
            return null;
        }
    }
    
    public CodigoFonte getPrincipalByIdExercicio(int idExercicio, int idAutor)
    {
        try {
            
            SQLQuery query = query("select * from CodigoFonte where IdExercicio = :idExercicio and IdAutor = :idAutor and Principal = 1").addEntity(CodigoFonte.class);
            query.setInteger("idExercicio", idExercicio);
            query.setInteger("idAutor", idAutor);
            return (CodigoFonte)query.list().get(0);
        } catch (Exception e) {
            return null;
        }
    }
    
    public boolean deleteNotIn(int idExercicio, int idAutor, String concatIds)
    {
        Transaction transaction_;
        Session session_;
        
        session_ = getSession();
        transaction_ = session_.beginTransaction();
        
        try {
            SQLQuery query = session_.createSQLQuery("delete from CodigoFonte where ID not in ("+ concatIds +") and IdExercicio = "+ idExercicio +" and IdAutor = " + idAutor);
            
            query.executeUpdate();
            
            return true;
        } catch (HibernateException e) { 
            transaction_.rollback();
            return false;
        } finally {
            session_.close();
        }
    }
    
}
