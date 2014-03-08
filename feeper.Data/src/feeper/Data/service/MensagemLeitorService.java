/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.Data.service;

import feeper.Data.entity.MensagemLeitor;
import feeper.Data.model.HibernateUtil;

/**
 *
 * @author
 * fabioalves
 */
public class MensagemLeitorService extends HibernateUtil<MensagemLeitor> {
    
    public MensagemLeitorService() {
        super(MensagemLeitor.class);
    }
    
}
