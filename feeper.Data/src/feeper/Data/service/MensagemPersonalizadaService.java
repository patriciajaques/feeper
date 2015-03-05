/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.Data.service;

import feeper.Data.entity.MensagemPersonalizada;
import feeper.Data.model.HibernateUtil;
import static feeper.Data.model.HibernateUtil.currentSession;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author gilvani
 */
public class MensagemPersonalizadaService extends HibernateUtil<MensagemPersonalizada> {

    public MensagemPersonalizadaService() {
        super(MensagemPersonalizada.class);
    }

    public List<MensagemPersonalizada> getbyIdAutor(int idAutor) {

        SQLQuery query = query("select * from MensagemPersonalizada where IdAutor=:idAutor").addEntity(MensagemPersonalizada.class);
        query.setInteger("idAutor", idAutor);
        return query.list();
    }
    
    public boolean SaveMensagens(int idAutor, List<MensagemPersonalizada> mensagens) {

        List<Integer> idsMensagens = new ArrayList<Integer>();

        for (MensagemPersonalizada mensagem : mensagens) {
            mensagem.setIdAutor(idAutor);

            Integer mensagemId = mensagem.getId();
            if (mensagemId != null && mensagemId > 0) {
                this.update(mensagem);
            } else {
                this.insert(mensagem);
            }
            idsMensagens.add(mensagem.getId());
        }

        return this.deleteNotIn(idAutor, idsMensagens);
    }

    public boolean deleteNotIn(int idAutor, List<Integer> ids) {
        
        Session session = currentSession();
        Transaction transaction = session.beginTransaction();

        try {
            SQLQuery query = null;
            if (ids.isEmpty()) {
                query = session.createSQLQuery("delete from MensagemPersonalizada where IdAutor = " + idAutor);
            } else {
                query = session.createSQLQuery("delete from MensagemPersonalizada where ID not in (" +ids.toString().replace("[", "").replace("]", "") + ") and IdAutor = " + idAutor);
            }

            query.executeUpdate();
            transaction.commit();

            return true;
        } catch (HibernateException e) {
            transaction.rollback();
            return false;
        }
    }
}
