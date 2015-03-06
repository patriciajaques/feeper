/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.Data.service;

import feeper.Data.entity.Tela;
import feeper.Data.model.HibernateUtil;
import java.util.List;
import org.hibernate.SQLQuery;
import org.hibernate.Transaction;

/**
 *
 * @author fabioalves
 */
public class TelaService extends HibernateUtil<Tela> {

    public TelaService() {
        super(Tela.class);
    }

    public int getIdTelaByDescricao(String descricao) {
        Transaction transaction = currentSession().beginTransaction();
        try {
            SQLQuery query = currentSession().createSQLQuery("select * from Tela where Descricao = :descricao ").addEntity(Tela.class);
            query.setString("descricao", descricao);
            List<Tela> lista = query.list();

            int id = 0;
            if (lista != null && lista.size() > 0) {
                id = lista.get(0).getId();
            }
            transaction.commit();
            return id;

        } catch (Exception e) {
            transaction.rollback();
            System.err.println(e.fillInStackTrace());
            return 0;
        }
    }

}
