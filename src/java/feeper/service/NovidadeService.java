/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.service;

import feeper.entity.Novidade;
import feeper.model.HibernateUtil;

public class NovidadeService extends HibernateUtil<Novidade> {
    
    public NovidadeService() {
        super(Novidade.class);
    }
    
    public int getCountMinhasNovidades(int idPessoa)
    {
        return 0;
    }
    
}
