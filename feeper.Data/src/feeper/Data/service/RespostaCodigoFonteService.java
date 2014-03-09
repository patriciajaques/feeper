/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.Data.service;

import feeper.Data.entity.RespostaCodigoFonte;
import feeper.Data.model.HibernateUtil;

/**
 *
 * @author
 * fabioalves
 */
public class RespostaCodigoFonteService extends HibernateUtil<RespostaCodigoFonte> {
    
    public RespostaCodigoFonteService() {
        super(RespostaCodigoFonte.class);
    }
    
}
