/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.Data.service;

import feeper.Data.entity.Tela;
import feeper.Data.model.HibernateUtil;
import java.util.List;
import org.hibernate.SQLQuery;

/**
 *
 * @author
 * fabioalves
 */
public class TelaService extends HibernateUtil<Tela> {
    
    public TelaService() {
        super(Tela.class);
    }
    
    public int getIdTelaByDescricao(String descricao)
    {
        SQLQuery query = query("select * from Tela where Descricao = :descricao ").addEntity(Tela.class);
        query.setString("descricao", descricao);
        List<Tela> lista = query.list();
        
        if (lista != null && lista.size() > 0)
            return lista.get(0).getId();
        return 0;
    }
    
}
