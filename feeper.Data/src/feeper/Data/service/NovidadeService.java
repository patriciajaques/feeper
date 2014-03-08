/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.Data.service;

import feeper.Data.entity.Novidade;
import feeper.Data.model.HibernateUtil;

public class NovidadeService extends HibernateUtil<Novidade> {
    
    public NovidadeService() {
        super(Novidade.class);
    }
    
    public int getCountMinhasNovidades(int idPessoa)
    {
        return 0;
    }
    
}
